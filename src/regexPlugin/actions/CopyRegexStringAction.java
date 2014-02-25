package regexPlugin.actions;

import regexPlugin.MatchAction;
import regexPlugin.RegexPanel;

import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class CopyRegexStringAction extends GenericAction {
  private MatchAction m_anAction;

  private JTextComponent m_pattern;

  public CopyRegexStringAction(final RegexPanel panel) {
    super(panel, "copyRegexString", panel.getIconCache().getIcon("copy.png"));
    m_pattern = panel.getPatternComponent();
    m_anAction = panel.getMatchAction();
  }

  public void perform() {
    final Clipboard cp = Toolkit.getDefaultToolkit().getSystemClipboard();
    final StringSelection sel = new StringSelection(m_pattern.getText());
    cp.setContents(sel, null);
  }
}

