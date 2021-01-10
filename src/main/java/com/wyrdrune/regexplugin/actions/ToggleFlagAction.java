package com.wyrdrune.regexplugin.actions;

import com.wyrdrune.regexplugin.IconCache;
import com.wyrdrune.regexplugin.MatchAction;
import com.wyrdrune.regexplugin.RegexPluginConfig;

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
