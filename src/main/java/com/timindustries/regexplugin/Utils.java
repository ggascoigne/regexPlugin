package com.timindustries.regexplugin;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.*;

import com.timindustries.regexplugin.ui.Resources;

public class Utils {

  /**
   * Trim all spaces from the end (rhs) of a string
   *
   * @param s The string to right-trim
   * @return The trimmed string
   */
  public static String trimRight(final String s) {
    int len = s.length();

    while (0 < len && s.charAt(len - 1) <= ' ') {
      len--;
    }

    return len < s.length() ? s.substring(0, len) : s;
  }

  public static void handleException(final String key, final Exception e) {
    e.printStackTrace();

    final String title = Resources.getTitle(key);
    StringWriter stacktrace = new StringWriter();
    PrintWriter out = new PrintWriter(stacktrace);
    e.printStackTrace(out);

    JOptionPane.showMessageDialog(null, title + '\n' + e.getMessage() + "\n" +
        stacktrace.toString(), "Error", JOptionPane.ERROR_MESSAGE);
  }
}
