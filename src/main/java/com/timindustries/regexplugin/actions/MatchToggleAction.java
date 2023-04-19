package com.timindustries.regexplugin.actions;

import javax.swing.*;

import com.timindustries.regexplugin.MatchAction;
import com.timindustries.regexplugin.RegexPanel;
import com.timindustries.regexplugin.RegexPluginConfig;

public abstract class MatchToggleAction extends GenericToggleAction {
  protected MatchAction fAction;

  protected RegexPluginConfig fConfig;

  public MatchToggleAction(final RegexPanel panel, final String key, final Icon icon,
      MatchAction action, final RegexPluginConfig config) {
    super(panel, key, icon);
    fAction = action;
    fConfig = config;
  }

  public void fireAction(boolean force) {
    if (force) {
      fAction.perform();
    }
  }

  public void perform() {
    fireAction(fConfig.isAutoUpdateEnabled());
  }

}
