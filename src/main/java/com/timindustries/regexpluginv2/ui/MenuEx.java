package com.timindustries.regexpluginv2.ui;

import javax.swing.*;

public class MenuEx extends JMenu {
  public MenuEx(final String resourceKey) {
    this(resourceKey, null);
  }

  public MenuEx(final String resourceKey, final Action a) {
    if (a != null) {
      setAction(a);
    }
    setText(Resources.getLabel(resourceKey));
  }
}
