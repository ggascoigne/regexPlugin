package com.wyrdrune.regexplugin.regexEditor;

/**
 * A token represents a smallest meaningful fragment of text, such as a word,
 * recognised by a scanner.
 * <p/>
 * This class is based on the work of Ian Holyer at the University of Bristol.
 * http://www.cs.bris.ac.uk/Teaching/Resources/COMS30122/tools/index.html
 */
public class Token {
  /**
   * The symbol contains all the properties shared with similar tokens.
   */
  public Symbol symbol;

  /**
   * The token's position is given by an index into the document text.
   */
  public int position;

  /**
   * Create a token with a given symbol and position.
   */
  Token(final Symbol symbol, final int position) {
    this.symbol = symbol;
    this.position = position;
  }
}
