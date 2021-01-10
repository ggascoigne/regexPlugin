package com.wyrdrune.regexplugin.ui;

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
