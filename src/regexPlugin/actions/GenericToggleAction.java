package regexPlugin.actions;

import regexPlugin.MatchAction;
import regexPlugin.RegexPluginConfig;
import regexPlugin.ui.Resources;

import javax.swing.*;

public abstract class GenericToggleAction {

  private Icon icon;
  private String key;

  protected GenericToggleAction(final String key, final Icon icon) {
    this.key = key;
    this.icon = icon;
  }

  public String getName() {
    return Resources.getLabel(key);
  }

  public String getDescription() {
    return Resources.getTooltip(key);
  }

  public Icon getIcon() {
    return icon;
  }

  public abstract boolean isSelected();

  public abstract void setSelected(boolean b);

}
