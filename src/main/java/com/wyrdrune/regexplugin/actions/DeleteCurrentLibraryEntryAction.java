package com.wyrdrune.regexplugin.actions;

import com.wyrdrune.regexplugin.LibraryManager;
import com.wyrdrune.regexplugin.RegexPanel;

public class DeleteCurrentLibraryEntryAction extends GenericAction {
  public DeleteCurrentLibraryEntryAction(final RegexPanel panel) {
    super(panel, "deleteCurrentLibEntry",
        panel.getIconCache().getIcon("delete.png"));
  }

  public void perform() {
    LibraryManager.delete(m_panel.getCurrentLibraryName());
  }
}

