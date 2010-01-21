package regexPlugin.regexEditor;

/**
 * A Symbol represents the information shared between similar tokens,
 * i.e. their type and spelling.
 * <p/>
 * This class is based on the work of Ian Holyer at the University of Bristol.
 * http://www.cs.bris.ac.uk/Teaching/Resources/COMS30122/tools/index.html
 */
public class Symbol {

  /**
   * The type is a small integer used to classify symbols.  It also
   * distinguishes different symbols with the same spelling, where necessary.
   */
  public int type;

  /**
   * The spelling.
   */
  public String name;

  /**
   * Construct a symbol from its type and name.
   */
  public Symbol(final int type, final String name) {
    this.type = type;
    this.name = name;
  }

  /**
   * Return the name of the symbol.
   */
  public String toString() {
    return name;
  }

  /**
   * Form a hash value from the type and name.
   */
  public int hashCode() {
    return name.hashCode() + type;
  }

  /**
   * Compare the type and name with some other symbol.
   */
  public boolean equals(final Object obj) {
    if (!(obj instanceof Symbol))
      return false;
    final Symbol that = (Symbol) obj;
    return name.equals(that.name) && type == that.type;
  }
}
