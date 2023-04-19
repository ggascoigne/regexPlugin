package com.timindustries.regexpluginv2;

import java.awt.*;

import javax.swing.*;

import com.timindustries.regexpluginv2.actions.GenericToggleAction;

public class ToggleSplitPane extends JPanel {
  private final JComponent fFirst;

  private final JComponent fOptional;

  private boolean fOptionalOn = false;

  private JSplitPane fSplitter;

  private final GenericToggleAction fToggleAction;

  private int fDesiredPosition = 0;

  public ToggleSplitPane(JComponent first, JComponent optional, IconCache iconCache) {
    fFirst = first;
    fOptional = optional;
    fToggleAction = new GenericToggleAction(null, "referenceOptional",
        iconCache.getIcon("reference.png")) {
      public boolean isSelected() {
        return fOptionalOn;
      }

      public void setSelected(boolean b) {
        setOptional(b);
      }

      public void perform() {
        //not used
      }
    };
    setLayout(new BorderLayout());
    initChildren();
  }

  private void initChildren() {
    if (fOptionalOn && fSplitter != null) {
      fDesiredPosition = fSplitter.getDividerLocation();
    }
    removeAll();

    if (fOptionalOn) {
      fSplitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, fFirst,
          fOptional);
      fSplitter.setBorder(null);
      // set this to 8, the default is far too large
      fSplitter.setDividerSize(8);
      fSplitter.setOneTouchExpandable(true);
      if (fDesiredPosition != 0) {
        fSplitter.setDividerLocation(fDesiredPosition);
      }

      add(fSplitter);
    } else {
      add(fFirst);
    }
    validate();
  }

  public GenericToggleAction getToggleAction() {
    return fToggleAction;
  }

  public int getDividerPos() {
    if (fSplitter == null) {
      return 0;
    } else {
      return fSplitter.getDividerLocation();
    }
  }

  public void setOptional(boolean referenceOn) {
    fOptionalOn = referenceOn;
    initChildren();
  }

  public void setDividerLocation(int referencePos) {
    if (fSplitter != null) {
      fSplitter.setDividerLocation(referencePos);
    } else {
      fDesiredPosition = referencePos;
    }
  }

  public boolean isOptionalOn() {
    return fOptionalOn;
  }
}
