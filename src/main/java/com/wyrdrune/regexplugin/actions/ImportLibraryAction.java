package com.wyrdrune.regexplugin.actions;

import com.wyrdrune.regexplugin.RegexPanel;

public class ImportLibraryAction extends GenericAction {

  public ImportLibraryAction(final RegexPanel panel) {
    super(panel, "importLibrary", panel.getIconCache().getIcon("open.png"));
  }

  public void perform() {
    LibraryActor.performImport();
  }

}
