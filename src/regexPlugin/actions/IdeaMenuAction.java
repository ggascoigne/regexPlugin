package regexPlugin.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class IdeaMenuAction extends AnAction {

    private GenericAction action;

  public IdeaMenuAction(GenericAction a) {
    super(a.getLabel(), null, a.getIcon());
    this.action = a;
  }

  public void actionPerformed(AnActionEvent event) {
    action.perform();
  }
}
