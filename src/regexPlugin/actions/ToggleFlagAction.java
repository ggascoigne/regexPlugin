package regexPlugin.actions;

import regexPlugin.IconCache;
import regexPlugin.MatchAction;
import regexPlugin.RegexPluginConfig;

public class ToggleFlagAction extends GenericToggleAction {
  private final int flag;
  private final MatchAction fAction;
  private final RegexPluginConfig fConfig;

  public ToggleFlagAction(final String key, final String iconResource, final int flag, final MatchAction action, IconCache iconCache, RegexPluginConfig config) {
    super(key, iconCache.getIcon(iconResource));
    fAction = action;
    fConfig = config;
    this.flag = flag;
  }

  public boolean isSelected() {
    return fAction.isSelected(flag);
  }

  public void setSelected(boolean b) {
    fAction.setFlag(flag, b);
    fireAction();
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
