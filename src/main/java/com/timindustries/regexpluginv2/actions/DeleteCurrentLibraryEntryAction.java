package com.timindustries.regexpluginv2.actions;

import com.timindustries.regexpluginv2.LibraryManager;
import com.timindustries.regexpluginv2.RegexPanel;

public class DeleteCurrentLibraryEntryAction extends GenericAction {
  public DeleteCurrentLibraryEntryAction(final RegexPanel panel) {
    super(panel, "deleteCurrentLibEntry",
        panel.getIconCache().getIcon("delete.png"));
  }

  public void perform() {
    LibraryManager.delete(m_panel.getCurrentLibraryName());
  }
}

