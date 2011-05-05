package regexPlugin.actions;

import regexPlugin.MatchAction;
import regexPlugin.RegexPanel;
import regexPlugin.Utils;

import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasteRegexStringAction extends GenericAction
{

    private MatchAction m_anAction;

    private JTextComponent m_pattern;

    private final Pattern javaStringPatternKillWS = Pattern.compile(
        "(?:                                                       \n" +
            " # First look for a non-escaped quote character           \n" +
            " (?:(?<!\\\\)\")                                          \n" +
            " # Then look for the string contents                      \n" +
            " (.*?)                                                    \n" +
            " # in this case ignoring all trailing whitespace          \n" +
            " (?:                                                      \n" +
            "  \\s*                                                    \n" +
            "   # and a possible literal \\n at the end of the string  \n" +
            "   (?:\\\\n)*                                             \n" +
            " )                                                        \n" +
            " # Finish with a closing non-escaped quote                \n" +
            " (?:(?<!\\\\)\")                                          \n" +
            " # Then allow this sequence to be repeated with plus      \n" +
            " # signs joining the strings                              \n" +
            " (?:\\s*\\+\\s*)*                                         \n" +
            ")                                                         \n",
        Pattern.COMMENTS | Pattern.MULTILINE );

    private final Pattern javaStringPatternPreserveWS = Pattern.compile(
        "(?:                                                  \n" +
            " # First look for a non-escaped quote character      \n" +
            " (?:(?<!\\\\)\")                                     \n" +
            "  # Then look for the string contents                \n" +
            "  (.*?)                                              \n" +
            "  # Finish with a closing non-escaped quote          \n" +
            " (?:(?<!\\\\)\")                                     \n" +
            " # Then allow this sequence to be repeated with plus \n" +
            " # signs joining the strings                         \n" +
            " (?:\\s*\\+\\s*)*                                    \n" +
            ")                                                    \n",
        Pattern.COMMENTS | Pattern.MULTILINE );

    public PasteRegexStringAction( final RegexPanel panel )
    {
        super( panel, "pasteRegexString", panel.getIconCache().getIcon( "paste.png" ) );
        m_anAction = panel.getMatchAction();
        m_pattern = panel.getPatternComponent();
    }

    public void perform()
    {
        final Transferable t = Toolkit.getDefaultToolkit()
            .getSystemClipboard()
            .getContents( null );
        if ( t != null )
        {
            if ( t.isDataFlavorSupported( DataFlavor.stringFlavor ) )
            {
                try
                {
                    final String s = (String)t.getTransferData(
                        DataFlavor.stringFlavor );
                    parseTextAndPaste( s );
                }
                catch ( UnsupportedFlavorException e )
                {
                    Utils.handleException( "error.pastRegex", e );
                }
                catch ( IOException e )
                {
                    Utils.handleException( "error.pastRegex", e );
                }
            }
        }
    }

    private String handleEscapes( final String s )
    {
        final StringBuffer sb = new StringBuffer();
        final int len = s.length();
        for ( int i = 0; i < len; i++ )
        {
            final char c = s.charAt( i );
            if ( c == '\\' )
            {
                final char next = s.charAt( ++i );
                switch ( next )
                {
                    case '\\':
                        sb.append( next );
                        break;
                    // On the assumption that any embedded \ns will be at the end of
                    // the string, I'm skipping this since we add an extra \n at the end
                    // of every line anyway.
//          case 'n':
//              sb.append( '\n' );
//              break;
                    case 't':
                        sb.append( '\t' );
                        break;
                    case '"':
                        sb.append( '"' );
                        break;
                    default:
                        break;
                }
            }
            else
            {
                sb.append( c );
            }
        }
        return sb.toString();
    }

    /**
     * parse through a clip of java source code identifying the regular
     * expression and pasting it into the pattern component.
     *
     * @param s The String to parse
     */
    private void parseTextAndPaste( final String s )
    {
        final StringBuffer sb = new StringBuffer();
        final boolean commentFlag =
            (m_anAction.getFlags() & Pattern.COMMENTS) == Pattern.COMMENTS;

        final Matcher matcher;
        if ( commentFlag ) matcher = javaStringPatternKillWS.matcher( s );
        else matcher = javaStringPatternPreserveWS.matcher( s );

        boolean foundMatch = false;
        while ( matcher.find() )
        {
            foundMatch = true;
            sb.append( handleEscapes( matcher.group( 1 ) ) ).append( '\n' );
        }
        if ( !foundMatch ) sb.append( s );

        m_pattern.setText( sb.toString() );
    }
}
