package com.timindustries.regexpluginv2.actions;

import com.timindustries.regexpluginv2.LibraryManager;
import com.timindustries.regexpluginv2.RegexPanel;

public class NewLibraryEntryAction extends GenericAction {
  public NewLibraryEntryAction(final RegexPanel panel) {
    super(panel, "newLibEntry", panel.getIconCache().getIcon("add.png"));
  }

  public void perform() {
    LibraryManager.newEntry();
  }
}
