package com.wyrdrune.regexplugin.actions;

import javax.swing.*;

import com.wyrdrune.regexplugin.MatchAction;
import com.wyrdrune.regexplugin.RegexPanel;
import com.wyrdrune.regexplugin.RegexPluginConfig;

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
