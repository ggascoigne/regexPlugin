package com.wyrdrune.regexplugin.actions;

import javax.swing.*;

import com.wyrdrune.regexplugin.RegexPanel;

public abstract class GenericToggleAction extends GenericAction {
  protected GenericToggleAction(final RegexPanel panel, final String key,
      final Icon icon) {
    super(panel, key, icon);
  }

  public boolean showDescription() {
    return false;
  }

  public abstract boolean isSelected();

  public abstract void setSelected(boolean b);

}
