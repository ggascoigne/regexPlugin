package com.wyrdrune.regexplugin.actions;

import com.wyrdrune.regexplugin.IconCache;
import com.wyrdrune.regexplugin.MatchAction;
import com.wyrdrune.regexplugin.RegexPluginConfig;

public class ToggleAutoUpdateAction extends MatchToggleAction {

  public ToggleAutoUpdateAction(MatchAction action, IconCache iconCache,
      RegexPluginConfig config) {
    super(null, "autoUpdate", iconCache.getIcon("autoupdate.png"), action,
        config);
  }

  public boolean isSelected() {
    return fConfig.autoUpdate;
  }

  public void setSelected(boolean b) {
    fConfig.autoUpdate = b;
    if (b) {
      fireAction(true);
    }
  }

}
