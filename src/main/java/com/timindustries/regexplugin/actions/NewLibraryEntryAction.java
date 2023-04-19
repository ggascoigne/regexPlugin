package com.timindustries.regexplugin.actions;

import com.timindustries.regexplugin.LibraryManager;
import com.timindustries.regexplugin.RegexPanel;

public class NewLibraryEntryAction extends GenericAction {
  public NewLibraryEntryAction(final RegexPanel panel) {
    super(panel, "newLibEntry", panel.getIconCache().getIcon("add.png"));
  }

  public void perform() {
    LibraryManager.newEntry();
  }
}
