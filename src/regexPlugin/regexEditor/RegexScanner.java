package regexPlugin.regexEditor;

public class RegexScanner extends Scanner {

  protected int read() {
    char c = buffer[start];
    int type = WHITESPACE;
    // Ignore the state, since there is only one.
    try {
      if (Character.isWhitespace(c)) {
        type = WHITESPACE;
        while (++start < end) {
          if (!Character.isWhitespace(buffer[start]))
            break;
        }
      } else if (c == '\\') {
        boolean done = false;
        type = CHARACTER;
        if (++start < end) {
          c = buffer[start];
          // grab the simple escapes first
          if ("\\tnrfae".indexOf(c) != -1) {
            done = true;
            start++;
          }

          if (!done) {
            // match octal escapes
            if (c == '0') {
              while (++start < end) {
                c = buffer[start];
                if (Character.isDigit(c))
                  continue;
                break;
              }
              done = true;
            }
          }
          if (!done) {
            // match hex escapes
            if (c == 'x' || c == 'u') {
              while (++start < end) {
                c = buffer[start];
                if (Character.isDigit(c) || "aAbBcCdDeEfF".indexOf(c) != -1)
                  continue;
                break;
              }
              done = true;
            }
          }
          if (!done) {
            if ("dDsSwW".indexOf(c) != -1) {
              type = CLASS;
              done = true;
              start++;
            }
          }
          if (!done) {
            if ("bBAGZz".indexOf(c) != -1) {
              type = BOUNDARY;
              done = true;
              start++;
            }
          }
          if (!done)
            start++;
        }
      } else if (c == '#') {
        type = COMMENT;
        while (++start < end) {
          if (buffer[start] == '\n')
            break;
        }
      } else if (c == '[') {
        type = CLASS;
        int braceCount = 1;
        char oldChar = c;
        while (++start < end) {
          oldChar = c;
          c = buffer[start];
          if (c == '[' && oldChar != '\\') {
            braceCount++;
          } else if (c == ']' && oldChar != '\\') {
            braceCount--;
            if (braceCount == 0) {
              start++;
              break;
            }
          } else if (c == '#' && oldChar != '\\') {
            break;
          }
        }
      } else if (c == '^' || c == '$') {
        type = BOUNDARY;
        start++;
      } else if (Character.isLetter(c)) {
        type = WORD;
        while (++start < end) {
          c = buffer[start];
          if (Character.isLetter(c) || Character.isDigit(c))
            continue;
          if (c == '-' || c == '\\' || c == '_')
            continue;
          break;
        }
      } else if (Character.isDigit(c)) {
        type = NUMBER;
        while (++start < end) {
          c = buffer[start];
          if (!Character.isDigit(c) && c != '.')
            break;
        }
      } else if (c == '(') {
        type = PUNCTUATION;
        start++;
        c = buffer[start];
        if (c == '?')
          start++;
      } else if (c == '+' || c == '*' || c == '?' || c == '|' || c == '.') {
        type = QUANTIFIER;
        start++;
      } else if ("){}:<>.=!,".indexOf(c) != -1) {
        type = PUNCTUATION;
        start++;
      } else {
        type = WORD;
        start++;
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      // then we fell off the array, but since the last item always gets rescanned, this is OK to ignore
    }
    return type;
  }
}
