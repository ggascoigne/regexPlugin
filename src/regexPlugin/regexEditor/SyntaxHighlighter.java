package regexPlugin.regexEditor;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.io.IOException;
import java.io.Reader;

/**
 * Display text with syntax highlighting.  Highlighting is done with full
 * accuracy, using a given language scanner.  Large amounts of re-highlighting
 * are done in small bursts to make sure the user interface doesn't freeze.
 * <p/>
 * This class is based on the work of Ian Holyer at the University of Bristol.
 * http://www.cs.bris.ac.uk/Teaching/Resources/COMS30122/tools/index.html
 */
public class SyntaxHighlighter extends JTextPane implements DocumentListener, TokenTypes
{
    private StyledDocument doc;

    private Scanner scanner;

    private int height, width;

    // An array of styles, indexed by token type.  Default styles are set up,
    // which can be used for any languages.

    Style[] styles;

    // Scan a small portion of the document.  If more is needed, call repaint()
    // so the GUI gets a go and doesn't freeze, but calls this again later.

    Segment text = new Segment();

    int firstRehighlightToken;

    int smallAmount = 100;

    /**
     * Create a graphics component which displays text with syntax highlighting.
     * Provide a width and height, in characters, and a language scanner.
     */
    public SyntaxHighlighter( StyledDocument doc, final int height, final int width,
        final Scanner scanner )
    {
        super( doc );
        this.doc = (StyledDocument)getDocument();
        this.height = height;
        this.width = width;
        this.scanner = scanner;
        doc.addDocumentListener( this );
        changeFont( new Font( "Monospaced", Font.PLAIN, getFont().getSize() ) );
        initStyles();
    }

    /**
     * Create a graphics component which displays text with syntax highlighting.
     * Provide a width and height, in characters, and a language scanner.
     */
    public SyntaxHighlighter( final int height, final int width, final Scanner scanner )
    {
        this( new DefaultStyledDocument(), height, width, scanner );
    }

    /**
     * Change the component's font, and change the size of the component to
     * match.
     */
    public void changeFont( final Font font )
    {
        setFont( font );
        final FontMetrics metrics = getFontMetrics( font );
        final int paneWidth = width * metrics.charWidth( 'm' );
        final int paneHeight = height * metrics.getHeight();
        final Dimension size = new Dimension( paneWidth, paneHeight );
        setMinimumSize( size );
        setPreferredSize( size );
        invalidate();
    }

    /**
     * Read new text into the component from a <code>Reader</code>.  Overrides
     * <code>read</code> in <code>JTextComponent</code> in order to highlight
     * the new text.
     */
    public void read( final Reader in, final Object desc ) throws IOException
    {
        final int oldLength = getDocument().getLength();
        doc.removeDocumentListener( this );
        super.read( in, desc );
        doc = (StyledDocument)getDocument();
        doc.addDocumentListener( this );
        final int newLength = getDocument().getLength();
        firstRehighlightToken = scanner.change( 0, oldLength, newLength );
        repaint();
    }

    public void setText( final String s )
    {
        final int oldLength = getDocument().getLength();
        doc.removeDocumentListener( this );
        super.setText( s );
        doc = (StyledDocument)getDocument();
        doc.addDocumentListener( this );
        final int newLength = getDocument().getLength();
        firstRehighlightToken = scanner.change( 0, oldLength, newLength );
        repaint();
    }

    private void initStyles()
    {
        styles = new Style[typeNames.length];
        changeStyle( UNRECOGNIZED, Color.red );
        changeStyle( WHITESPACE, Color.black );
        changeStyle( WORD, Color.black );
        changeStyle( NUMBER, Color.black );
        changeStyle( PUNCTUATION, new Color( 102, 14, 122 ), Font.BOLD );
        changeStyle( COMMENT, Color.gray, Font.ITALIC );
        changeStyle( CHARACTER, Color.magenta );
        changeStyle( CLASS, new Color( 0, 0, 128 ), Font.BOLD );
        changeStyle( BOUNDARY, Color.blue );
        changeStyle( QUANTIFIER, Color.blue, Font.BOLD );

        for ( int i = 0; i < styles.length; i++ )
        {
            if ( styles[i] == null ) styles[i] = styles[WHITESPACE];
        }
    }

    /**
     * Change the style of a particular type of token.
     */
    public void changeStyle( final int type, final Color color )
    {
        final Style style = addStyle( typeNames[type], null );
        StyleConstants.setForeground( style, color );
        styles[type] = style;
    }

    /**
     * Change the style of a particular type of token, including adding bold or
     * italic using a third argument of <code>Font.BOLD</code> or
     * <code>Font.BOLD</code> or the bitwise union
     * <code>Font.BOLD|Font.ITALIC</code>.
     */
    public void changeStyle( final int type, final Color color, final int fontStyle )
    {
        final Style style = addStyle( typeNames[type], null );
        StyleConstants.setForeground( style, color );
        if ( (fontStyle & Font.BOLD) != 0 ) StyleConstants.setBold( style, true );
        if ( (fontStyle & Font.ITALIC) != 0 ) StyleConstants.setItalic( style, true );
        styles[type] = style;
    }

    /**
     * <font style='color:gray;'>Ignore this method. Responds to the
     * underlying document changes by re-highlighting.</font>
     */
    public void insertUpdate( final DocumentEvent e )
    {
        final int offset = e.getOffset();
        final int length = e.getLength();
        firstRehighlightToken = scanner.change( offset, 0, length );
        repaint();
    }

    /**
     * <font style='color:gray;'>Ignore this method. Responds to the
     * underlying document changes by re-highlighting.</font>
     */
    public void removeUpdate( final DocumentEvent e )
    {
        final int offset = e.getOffset();
        final int length = e.getLength();
        firstRehighlightToken = scanner.change( offset, length, 0 );
        repaint();
    }

    /**
     * <font style='color:gray;'>Ignore this method. Responds to the
     * underlying document changes by re-highlighting.</font>
     */
    public void changedUpdate( final DocumentEvent e )
    {
        // Do nothing.
    }

    /**
     * <font style='color:gray;'>Ignore this method. Carries out a small
     * amount of re-highlighting for each call to <code>repaint</code>.</font>
     */
    protected void paintComponent( final Graphics g )
    {
        super.paintComponent( g );
        int offset = scanner.position();
        if ( offset < 0 ) return;

        int tokensToRedo = 0;
        int amount = smallAmount;
        while ( tokensToRedo == 0 && offset >= 0 )
        {
            int length = doc.getLength() - offset;
            if ( length > amount ) length = amount;
            try
            {
                doc.getText( offset, length, text );
            }
            catch ( BadLocationException e )
            {
                return;
            }
            tokensToRedo = scanner.scan( text.array, text.offset, text.count );
            offset = scanner.position();
            amount = 2 * amount;
        }
        for ( int i = 0; i < tokensToRedo; i++ )
        {
            final Token t = scanner.getToken( firstRehighlightToken + i );
            final int length = t.symbol.name.length();
            int type = t.symbol.type;
            if ( type < 0 ) type = UNRECOGNIZED;
            doc.setCharacterAttributes( t.position, length, styles[type], true );
        }
        firstRehighlightToken += tokensToRedo;
        if ( offset >= 0 ) repaint( 2 );
    }
}
