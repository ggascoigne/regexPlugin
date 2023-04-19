package com.timindustries.regexplugin.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import com.timindustries.regexplugin.Utils;

public class Regex2JavaString {
  public static String escape(final String s) {
    final StringBuffer sb = new StringBuffer();
    for (int i = 0; i < s.length(); i++) {
      final char c = s.charAt(i);

      if (c == '\\') {
        sb.append("\\\\");
      } else if (c == '"') {
        sb.append("\\\"");
      } else {
        sb.append(c);
      }
    }
    return sb.toString();
  }

  public static String quote(final String s, int flags) {
    final boolean commentFlag = (flags & Pattern.COMMENTS) == Pattern.COMMENTS;
    final String spaces =
        "                                                                                                                      ";
    final StringBuffer sb = new StringBuffer();
    final Collection list = splitToJavaLines(s, commentFlag);
    int longestLength = 0;
    for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
      final String s1 = (String) iterator.next();
      if (s1.length() > longestLength) {
        longestLength = s1.length();
      }
    }
    for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
      final String s1 = (String) iterator.next();
      if (commentFlag) {
        sb.append('"')
            .append(s1)
            .append(spaces.substring(0, longestLength - s1.length() + 2));
        if (!iterator.hasNext()) {
          sb.append("\\n\"\n");
        } else {
          sb.append("\\n\" +\n");
        }
      } else {
        sb.append('"').append(s1);
        if (!iterator.hasNext()) {
          sb.append("\"\n");
        } else {
          sb.append("\" +\n");
        }
      }
    }
    return sb.toString();
  }

  /**
   * Split a string into lines, breaking at end of line characters. Escaping
   * appropriate characters as we go.
   *
   * @param in Input string to be split
   * @return An array of strings, one for each line, with the eol characters stripped.
   */
  static public Collection splitToJavaLines(final String in,
      final boolean trimTrailingSpaces) {
    final ArrayList list = new ArrayList();
    final StringTokenizer st = new StringTokenizer(in, "\n");
    while (st.hasMoreTokens()) {
      String s = st.nextToken();
      if (trimTrailingSpaces) {
        s = Utils.trimRight(s);
      }
      list.add(escape(s));
    }
    return list;
  }
}
