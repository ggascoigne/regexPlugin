package com.timindustries.regexpluginv2.regexEditor;

import com.timindustries.regexpluginv2.MatchAction;

public class RegexEditor extends SyntaxHighlighter {

  public RegexEditor(MatchAction matchAction) {
    super(20, 20, new RegexScanner());
    addCaretListener(new ParenHighlighter());
    setTransferHandler(new EditorTransferHandler("text", matchAction));
  }

}
