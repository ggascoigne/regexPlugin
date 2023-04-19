package com.timindustries.regexpluginv2.regexEditor;

/**
 * The TokenTypes interface defines the integer constants representing
 * different types of tokens, for use with any languages.  The constants are
 * used in symbols to represent the types of similar tokens, and in scanners as
 * scanner states, and in highlighters to determine the colour or style of
 * tokens.  There is also an array typeNames of textual names, indexed by type,
 * for descriptive purposes.
 * <p/>
 * <p>The UNRECOGNIZED constant (zero) is for tokens which are completely
 * unrecognized, usually consisting of a single illegal character.  Other error
 * tokens are represented by negative types, where -t represents an incomplete
 * or malformed token of type t.  An error token usually consists of the
 * maximal legal substring of the source text.
 * <p/>
 * <p>The WHITESPACE constant is used to classify tokens which are to be
 * discarded, it acts as a suitable scanner state at the beginning of a
 * document, and it is used for the usual end-of-text sentinel token which
 * marks the end of the document.  Comments can optionally be classified as
 * WHITESPACE and discarded, if they are not needed for highlighting.  No other
 * types besides UNRECOGNIZED and WHITESPACE are treated specially.
 * <p/>
 * <p>The constants are presented as an interface so that any class can
 * implement the interface and use the names of the constants directly, without
 * prefixing them with a class name.
 * <p/>
 * This class is based on the work of Ian Holyer at the University of Bristol.
 * http://www.cs.bris.ac.uk/Teaching/Resources/COMS30122/tools/index.html
 */
public enum TokenType {
  UNRECOGNIZED("bad token"),
  WHITESPACE("whitespace"),
  WORD("word"),
  NUMBER("number"),
  PUNCTUATION("punctuation"),
  COMMENT("comment"),
  CHARACTER("character"),
  CLASS("class"),
  BOUNDARY("boundary"),
  QUANTIFIER("quantifier");

  private final String name;

  TokenType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
