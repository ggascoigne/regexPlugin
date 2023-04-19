package com.timindustries.regexpluginv2.ui;

import javax.swing.*;

public class LabelEx extends JLabel {
  public LabelEx(final String resourceKey) {
    super(Resources.getLabel(resourceKey));
    final String tt = Resources.getTooltip(resourceKey);
    if (tt != null) {
      setToolTipText(tt);
    }
  }
}
