package com.timindustries.regexplugin.uiInterface;

public class ComponentFactory {
  private ComponentFactory() {
  }

  public static ComponentManager getInstance() {
    return ComponentManager.createOrGetInstance();
  }
}
