package regexPlugin.actions;

import regexPlugin.IconCache;
import regexPlugin.MatchAction;
import regexPlugin.RegexPluginConfig;

public class ReplaceAllAction extends GenericToggleAction {

  private RegexPluginConfig fConfig;
  private MatchAction fAction;

  public ReplaceAllAction(MatchAction action, IconCache icons, RegexPluginConfig config) {
    super("replaceAll", icons.getIcon("replaceall.png"));
    fConfig = config;
    fAction = action;
  }

  public boolean isSelected() {
    return fAction.isReplaceAll();
  }

  public void setSelected(boolean b) {
    fAction.setReplaceAll(b);
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
