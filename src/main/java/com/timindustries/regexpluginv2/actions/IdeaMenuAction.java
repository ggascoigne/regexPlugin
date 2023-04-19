package com.timindustries.regexpluginv2.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class IdeaMenuAction extends AnAction {

  private final GenericAction action;

  public IdeaMenuAction(GenericAction a) {
    super(a.getName(), null, a.getIcon());
    this.action = a;
  }

  public void actionPerformed(AnActionEvent event) {
    action.perform();
  }
}
