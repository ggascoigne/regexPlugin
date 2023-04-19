package com.timindustries.regexpluginv2.ui;

import javax.swing.*;

public class CheckBoxEx extends JCheckBox {
  public CheckBoxEx(final String resourceKey, final boolean selected) {
    super(Resources.getLabel(resourceKey), selected);
    final String tt = Resources.getTooltip(resourceKey);
    if (tt != null) {
      setToolTipText(tt);
    }
  }
}
