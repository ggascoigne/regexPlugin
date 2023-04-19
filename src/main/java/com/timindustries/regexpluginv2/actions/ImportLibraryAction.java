package com.timindustries.regexpluginv2.actions;

import com.timindustries.regexpluginv2.RegexPanel;

public class ImportLibraryAction extends GenericAction {

  public ImportLibraryAction(final RegexPanel panel) {
    super(panel, "importLibrary", panel.getIconCache().getIcon("open.png"));
  }

  public void perform() {
    LibraryActor.performImport();
  }

}
