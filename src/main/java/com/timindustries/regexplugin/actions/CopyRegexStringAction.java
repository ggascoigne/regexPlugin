package com.timindustries.regexplugin.actions;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.text.JTextComponent;

import com.timindustries.regexplugin.RegexPanel;

public class CopyRegexStringAction extends GenericAction {

  private final JTextComponent m_pattern;

  public CopyRegexStringAction(final RegexPanel panel) {
    super(panel, "copyRegexString", panel.getIconCache().getIcon("copy.png"));
    m_pattern = panel.getPatternComponent();
  }

  public void perform() {
    final Clipboard cp = Toolkit.getDefaultToolkit().getSystemClipboard();
    final StringSelection sel = new StringSelection(m_pattern.getText());
    cp.setContents(sel, null);
  }
}

