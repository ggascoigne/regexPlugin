package com.timindustries.regexpluginv2.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class IdeaAction extends AnAction {

  private final GenericAction action;

  public IdeaAction(GenericAction a) {
    super(a.getDescription(), a.getName(), a.getIcon());
    this.action = a;
  }

  public void actionPerformed(AnActionEvent event) {
    action.perform();
  }
}
