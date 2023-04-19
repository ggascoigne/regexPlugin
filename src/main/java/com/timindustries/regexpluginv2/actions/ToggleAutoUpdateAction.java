package com.timindustries.regexpluginv2.actions;

import com.timindustries.regexpluginv2.IconCache;
import com.timindustries.regexpluginv2.MatchAction;
import com.timindustries.regexpluginv2.RegexPluginConfig;

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
