package com.timindustries.regexpluginv2.actions;

import javax.swing.*;

import com.timindustries.regexpluginv2.MatchAction;
import com.timindustries.regexpluginv2.RegexPanel;
import com.timindustries.regexpluginv2.RegexPluginConfig;

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
