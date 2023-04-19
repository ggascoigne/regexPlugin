package com.timindustries.regexpluginv2.actions;

import com.timindustries.regexpluginv2.IconCache;
import com.timindustries.regexpluginv2.MatchAction;
import com.timindustries.regexpluginv2.RegexPluginConfig;

public class ReplaceAllAction extends MatchToggleAction {

  public ReplaceAllAction(MatchAction action, IconCache icons,
      RegexPluginConfig config) {
    super(null, "replaceAll", icons.getIcon("replaceall.png"), action, config);
  }

  public boolean isSelected() {
    return fAction.isReplaceAll();
  }

  public void setSelected(boolean b) {
    fAction.setReplaceAll(b);
    perform();
  }

  public boolean showDescription() {
    return fConfig.showLabels;
  }

}
