package com.wyrdrune.regexplugin.regexEditor;

import com.wyrdrune.regexplugin.MatchAction;

public class RegexEditor extends SyntaxHighlighter {

  public RegexEditor(MatchAction matchAction) {
    super(20, 20, new RegexScanner());
    addCaretListener(new ParenHighlighter());
    setTransferHandler(new EditorTransferHandler("text", matchAction));
  }

}
