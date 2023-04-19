package com.timindustries.regexpluginv2.actions;

import com.timindustries.regexpluginv2.IconCache;
import com.timindustries.regexpluginv2.MatchAction;
import com.timindustries.regexpluginv2.RegexPluginConfig;

public class ToggleFlagAction extends MatchToggleAction {
  private final int flag;

  public ToggleFlagAction(final String key, final String iconResource, final int flag,
      final MatchAction action, IconCache iconCache,
      RegexPluginConfig config) {
    super(null, key, iconCache.getIcon(iconResource), action, config);
    this.flag = flag;
  }

  public boolean isSelected() {
    return fAction.isSelected(flag);
  }

  public void setSelected(boolean b) {
    fAction.setFlag(flag, b);
    perform();
  }

  public boolean showDescription() {
    return fConfig.showLabels;
  }

}
