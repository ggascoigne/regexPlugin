package regexPlugin.regexEditor;

import com.intellij.openapi.editor.RawText;
import regexPlugin.MatchAction;
import regexPlugin.actions.Regex2JavaString;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.im.InputContext;
import java.io.*;

public class EditorTransferHandler extends TransferHandler
{
    private static final int BUF_SIZE = 1024;

    private MatchAction fAction;

    public EditorTransferHandler( String property, MatchAction action )
    {
        super( property );
        fAction = action;
    }

    public void exportToClipboard( JComponent comp, Clipboard clip, int action )
    {
        clip.setContents( new RegexSelection( ((RegexEditor)comp).getSelectedText(),
            fAction.getFlags() ), null );
    }

    public boolean importData( JComponent comp, Transferable t )
    {
        if ( comp instanceof JTextComponent )
        {
            final DataFlavor flavor = getFlavor( t );
            if ( flavor != null )
            {
                final InputContext ic = comp.getInputContext();
                if ( ic != null )
                {
                    ic.endComposition();
                }
                ((JTextComponent)comp).replaceSelection(
                    stripHtmlSpaces( extractTextFromFlavor( flavor, t ) ) );
                return true;
            }
        }
        return false;
    }

    // The trick here is to provide a default access that convert the regex to a java string, but provide
    // an undecorated version that can be pasted back into the editor again without being mucked with.
    public static class RegexSelection implements Transferable
    {
        static final DataFlavor regexFlavor = new DataFlavor( RegexSelection.class,
            "regex" );

        DataFlavor flavors[] = {
            DataFlavor.stringFlavor,    // default decorated flavor
            regexFlavor,
            // raw regex flavor - used for internal pastes into the regex window
            RawText.FLAVOR
            // raw regex flavor (Idea specific) - used for paste-special in Idea to paste raw text
        };

        private String data;

        private int flags;

        public RegexSelection( String data, int flags )
        {
            this.data = data;
            this.flags = flags;
        }

        public DataFlavor[] getTransferDataFlavors()
        {
            return flavors;
        }

        public boolean isDataFlavorSupported( DataFlavor flavor )
        {
            for ( int i = 0; i < flavors.length; i++ )
            {
                final DataFlavor dataFlavor = flavors[i];
                if ( dataFlavor.equals( flavor ) ) return true;
            }
            return false;
        }

        // Returns Image object housed by Transferable object
        public Object getTransferData(
            DataFlavor flavor ) throws UnsupportedFlavorException, IOException
        {
            if ( !isDataFlavorSupported( flavor ) )
            {
                throw new UnsupportedFlavorException( flavor );
            }
            if ( flavor.equals( regexFlavor ) ) return data;
            else if ( flavor.equals(
                RawText.FLAVOR ) ) // allows paste-simple to work in Idea
                return new RawText( data );
            else return Regex2JavaString.quote( data, flags );
        }
    }

    private String extractTextFromFlavor( final DataFlavor flavor, final Transferable t )
    {
        String text = "";
        try
        {
            if ( flavor.isRepresentationClassInputStream() )
            {
                try
                {
                    final InputStream is = (InputStream)t.getTransferData( flavor );
                    final BufferedReader reader = new BufferedReader(
                        new InputStreamReader( is ) );
                    final char[] ca = new char[is.available()];
                    reader.read( ca, 0, is.available() );
                    text = new String( ca );
                }
                catch ( ClassCastException e )
                {
                    //System.out.println("flavor" + flavor + "not accessible as InputStream");
                }
            }
            else if ( flavor.isRepresentationClassReader() )
            {
                final Reader reader = (Reader)t.getTransferData( flavor );

                // read the source entirely into a bufer
                final StringWriter tmpWriter = new StringWriter( BUF_SIZE );
                final char[] buf = new char[BUF_SIZE];
                int read;
                while ( (read = reader.read( buf )) >= 0 )
                {
                    tmpWriter.write( buf, 0, read );
                }
                text = tmpWriter.getBuffer().toString();
            }
            else if ( flavor.isRepresentationClassCharBuffer() ||
                flavor.isRepresentationClassByteBuffer() ||
                flavor.isRepresentationClassSerializable() ||
                (flavor.equals( RegexSelection.regexFlavor )) )
            {
                try
                {
                    text = (String)t.getTransferData( flavor );
                }
                catch ( ClassCastException e )
                {
                    //System.out.println("flavor" + flavor + "not accessible as String");
                }
            }
        }
        catch ( UnsupportedFlavorException ufe )
        {
            System.out.println( "UnsupportedFlavorException" );
        }
        catch ( IOException ioe )
        {
            System.out.println( "IOException" );
        }
        return text;
    }

    private String stripHtmlSpaces( String data )
    {
        final char[] chars = new char[data.length()];
        data.getChars( 0, data.length(), chars, 0 );
        for ( int i = 0; i < chars.length; i++ )
        {
            if ( chars[i] == 160 ) // non-break space
                chars[i] = ' ';
        }
        return new String( chars, 0, chars.length );
    }

    private DataFlavor getFlavor( Transferable t )
    {
        final DataFlavor[] flavors = t.getTransferDataFlavors();
        if ( flavors != null )
        {
            //System.out.println("flavor count = " + flavors.length);
            //for (int counter = 0; counter < flavors.length; counter++) {
            //  System.out.println("flavors[" + counter + "]" + flavors[counter]);
            //  System.out.println("text = " + extractTextFromFlavor(flavors[counter], t));
            //}

            // Look for our regex flavor first
            for ( int counter = 0; counter < flavors.length; counter++ )
            {
                if ( flavors[counter].equals( RegexSelection.regexFlavor ) )
                {
                    return flavors[counter];
                }
            }

            // if we can't find one then fall back on string instead.
            for ( int counter = 0; counter < flavors.length; counter++ )
            {
                if ( flavors[counter].equals( DataFlavor.stringFlavor ) )
                {
                    return flavors[counter];
                }
            }
        }
        return null;
    }

}
