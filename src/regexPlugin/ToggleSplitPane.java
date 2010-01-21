package regexPlugin;

import regexPlugin.actions.GenericToggleAction;

import javax.swing.*;
import java.awt.*;

public class ToggleSplitPane extends JPanel {
  private JComponent fFirst;
  private JComponent fOptional;
  private boolean fOptionalOn = false;
  private JSplitPane fSplitter;
  private GenericToggleAction fToggleAction;

  public ToggleSplitPane(JComponent first, JComponent optional, IconCache iconCache) {
    fFirst = first;
    fOptional = optional;
    fToggleAction = new GenericToggleAction("referenceOptional", iconCache.getIcon("reference.png")) {
      public boolean isSelected() {
        return fOptionalOn;
      }
      public void setSelected(boolean b) {
        setOptional(b);
      }
    };
    setLayout(new BorderLayout());
    initChilds();
  }

  private void initChilds() {
    removeAll();

    if (fOptionalOn) {
      fSplitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, fFirst, fOptional);
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
    initChilds();
  }

  public void setDividerLocation(int referencePos) {
    if (fSplitter != null) {
      fSplitter.setDividerLocation(referencePos);
    }
  }

  public boolean isOptionalOn() {
    return fOptionalOn;
  }
}
