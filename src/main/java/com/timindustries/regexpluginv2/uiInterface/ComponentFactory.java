package com.timindustries.regexpluginv2.uiInterface;

public class ComponentFactory {
  private ComponentFactory() {
  }

  public static ComponentManager getInstance() {
    return ComponentManager.createOrGetInstance();
  }
}
