package regexPlugin.actions;

import regexPlugin.IconCache;
import regexPlugin.MatchAction;
import regexPlugin.RegexPluginConfig;

public class ToggleAutoUpdateAction extends GenericToggleAction {
  private MatchAction fAction;
  private RegexPluginConfig fConfig;

  public ToggleAutoUpdateAction(RegexPluginConfig config, MatchAction action, IconCache iconCache) {
    super("autoUpdate", iconCache.getIcon("autoupdate.png"));
    this.fConfig = config;
    this.fAction = action;
  }

  public boolean isSelected() {
    return fConfig.autoUpdate;
  }

  public void setSelected(boolean b) {
    fConfig.autoUpdate = b;
    if (b == true) {
      fireAction(true);
    }
  }

  public void fireAction() {
    fireAction(fConfig.isAutoUpdateEnabled());
  }

  public void fireAction(boolean force) {
    if (force == true) {
      fAction.perform();
    }
  }
}
