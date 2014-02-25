package regexPlugin.regexEditor;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.*;
import java.awt.*;

/**
 * A class to support highlighting of parenthesis.  To use it, add it as a
 * caret listener to your text component.
 * <p/>
 * It listens for the location of the dot.  If the character before the dot
 * is a close paren, it finds the matching m_start paren and highlights both
 * of them.  Otherwise it clears the highlighting.
 * <p/>
 * This object can be shared among multiple components.  It will only
 * highlight one at a time.
 * <p/>
 * Large chunks of this code are based on a sample written by By Joshua Engel and
 * posted to http://www.informit.com/, thanks.
 */
public class ParenHighlighter implements CaretListener {
  /**
   * The tags returned from the m_highlighter, used for clearing the
   * current highlight.
   */
  protected Object m_start, m_end;

  /**
   * The last m_highlighter used
   */
  protected Highlighter m_highlighter;

  /**
   * Used to paint good parenthesis matches
   */
  protected Highlighter.HighlightPainter m_goodPainter;

  /**
   * Used to paint bad parenthesis matches
   */
  protected Highlighter.HighlightPainter m_badPainter;

  /**
   * Highlights using a good painter for matched parens, and a bad
   * painter for unmatched parens
   */
  public ParenHighlighter(final Highlighter.HighlightPainter goodHighlightPainter,
      final Highlighter.HighlightPainter badHighlightPainter) {
    m_goodPainter = goodHighlightPainter;
    m_badPainter = badHighlightPainter;
  }

  /**
   * A ParenHighlighter with the default highlighters (cyan and magenta)
   */
  public ParenHighlighter() {
    this(
        new DefaultHighlighter.DefaultHighlightPainter(new Color(153, 204, 255)),
        new DefaultHighlighter.DefaultHighlightPainter(
            new Color(255, 220, 220)));
  }

  public void clearHighlights() {
    if (m_highlighter != null) {
      if (m_start != null) {
        m_highlighter.removeHighlight(m_start);
      }
      if (m_end != null) {
        m_highlighter.removeHighlight(m_end);
      }
      m_start = null;
      m_end = null;
      m_highlighter = null;
    }
  }

  /**
   * Returns the character at position p in the document
   */
  public static char getCharAt(final Document doc,
      final int p) throws BadLocationException {
    return doc.getText(p, 1).charAt(0);
  }

  private static final boolean isOpenParen(final char c) {
    return c == '(' || c == '[' || c == '{';
  }

  private static final boolean isCloseParen(final char c) {
    return c == ')' || c == ']' || c == '}';
  }

  /**
   * Check that the preceding character doesn't escape whatever is at i, this means make sure that the
   * character at i-1 isn't a non-escaped \
   *
   * @param doc Doc to retrieve characters from
   * @param i   location of the current character
   * @return true is the character is not escaped
   */
  private static boolean notEscaped(final Document doc, final int i) {
    try {
      char c = getCharAt(doc, i - 1);
      if (c != '\\') {
        return true;
      }
      c = getCharAt(doc, i - 2);
      if (c != '\\') {
        return false;
      }
    } catch (BadLocationException e)        // if we get a bad location then we didn't have an escape char :)
    {
    }
    return true;
  }

  /**
   * Returns the position of the matching parenthesis (bracket,
   * whatever) for the character at paren.  It counts all kinds of
   * brackets, so the "matching" parenthesis might be a bad one.
   * <p/>
   * It's assumed that paren is the position of some parenthesis
   * character
   *
   * @return the position of the matching paren, or -1 if none is found
   */
  public static int findPrevMatchingParen(final Document doc,
      final int paren) throws BadLocationException {
    int parenCount = 1;
    int i = paren - 1;
    for (; i >= 0; i--) {
      final char c = getCharAt(doc, i);
      if (isOpenParen(c) && notEscaped(doc, i)) {
        parenCount--;
      } else if (isCloseParen(c) && notEscaped(doc, i)) {
        parenCount++;
      }
      if (parenCount == 0) {
        break;
      }
    }
    return i;
  }

  /**
   * Returns the position of the matching parenthesis (bracket,
   * whatever) for the character at paren.  It counts all kinds of
   * brackets, so the "matching" parenthesis might be a bad one.
   * <p/>
   * It's assumed that paren is the position of some parenthesis
   * character
   *
   * @return the position of the matching paren, or -1 if none is found
   */
  public static int findNextMatchingParen(final Document doc,
      final int paren) throws BadLocationException {
    int parenCount = 1;
    int i = paren + 1;
    for (; i <= doc.getLength(); i++) {
      final char c = getCharAt(doc, i);
      if (isOpenParen(c) && notEscaped(doc, i)) {
        parenCount++;
      } else if (isCloseParen(c) && notEscaped(doc, i)) {
        parenCount--;
      }
      if (parenCount == 0) {
        break;
      }
    }
    return i;
  }

  /**
   * Called whenever the caret moves, it updates the highlights
   */
  public void caretUpdate(final CaretEvent e) {
    clearHighlights();
    final JTextComponent source = (JTextComponent) e.getSource();
    m_highlighter = source.getHighlighter();
    final Document doc = source.getDocument();
    if (e.getDot() == 0) {
      return;
    }

    // The character we want is the one before the current position
    final int pointA = e.getDot() - 1;
    int pointB = -1;
    try {
      boolean highlight = false;
      char c = getCharAt(doc, pointA);
      char c2 = 0;
      if (isCloseParen(c) && notEscaped(doc, pointA)) {
        pointB = findPrevMatchingParen(doc, pointA);
        try {
          c2 = getCharAt(doc, pointB);
        } catch (Exception e1) {
        }
        highlight = true;
      } else if (isOpenParen(c) && notEscaped(doc, pointA)) {
        pointB = findNextMatchingParen(doc, pointA);
        c2 = c;
        c = getCharAt(doc, pointB);
        highlight = true;
      }

      if (highlight) {
        if (pointB >= 0) {
          if (c2 == '(' && c == ')' || c2 == '{' && c == '}' ||
              c2 == '[' && c == ']') {
            m_start = m_highlighter.addHighlight(pointB, pointB + 1,
                m_goodPainter);
            m_end = m_highlighter.addHighlight(pointA, pointA + 1,
                m_goodPainter);
          } else {
            m_start = m_highlighter.addHighlight(pointB, pointB + 1,
                m_badPainter);
            m_end = m_highlighter.addHighlight(pointA, pointA + 1,
                m_badPainter);
          }
        } else {
          m_end = m_highlighter.addHighlight(pointA, pointA + 1,
              m_badPainter);
        }
      }
    } catch (BadLocationException ex) {
    }
  }
}
