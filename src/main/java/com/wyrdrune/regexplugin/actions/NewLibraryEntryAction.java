package com.wyrdrune.regexplugin.actions;

import com.wyrdrune.regexplugin.LibraryManager;
import com.wyrdrune.regexplugin.RegexPanel;

public class NewLibraryEntryAction extends GenericAction {
  public NewLibraryEntryAction(final RegexPanel panel) {
    super(panel, "newLibEntry", panel.getIconCache().getIcon("add.png"));
  }

  public void perform() {
    LibraryManager.newEntry();
  }
}
