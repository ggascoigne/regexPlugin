package regexPlugin.regexEditor;

import regexPlugin.MatchAction;

public class RegexEditor extends SyntaxHighlighter
{

    public RegexEditor( MatchAction matchAction )
    {
        super( 20, 20, new RegexScanner() );
        addCaretListener( new ParenHighlighter() );
        setTransferHandler( new EditorTransferHandler( "text", matchAction ) );
    }

}
