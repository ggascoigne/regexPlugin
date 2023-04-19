package com.timindustries.regexpluginv2.actions;

import javax.swing.*;

import com.timindustries.regexpluginv2.RegexPanel;
import com.timindustries.regexpluginv2.ui.Resources;

public abstract class GenericAction {
  protected RegexPanel m_panel;

  private final String m_key;

  private final Icon m_icon;

  public GenericAction(final RegexPanel panel, final String key, final Icon icon) {
    m_panel = panel;
    m_key = key;
    m_icon = icon;
  }

  public abstract void perform();

  public String getName() {
    return Resources.getLabel(m_key);
  }

  public String getDescription() {
    return Resources.getTooltip(m_key);
  }

  public Icon getIcon() {
    return m_icon;
  }

}
