package com.timindustries.regexpluginv2.actions;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.text.JTextComponent;

import com.timindustries.regexpluginv2.MatchAction;
import com.timindustries.regexpluginv2.RegexPanel;

public class CopyQuotedJavaRegexStringAction extends GenericAction {
  private final MatchAction m_anAction;

  private final JTextComponent m_pattern;

  public CopyQuotedJavaRegexStringAction(final RegexPanel panel) {
    super(panel, "copyQuotedJavaRegexString", panel.getIconCache().getIcon("copy.png"));
    m_pattern = panel.getPatternComponent();
    m_anAction = panel.getMatchAction();
  }

  public void perform() {
    final Clipboard cp = Toolkit.getDefaultToolkit().getSystemClipboard();
    final StringSelection sel = new StringSelection(
        Regex2JavaString.quote(m_pattern.getText(), m_anAction.getFlags()));
    cp.setContents(sel, null);
  }

}

