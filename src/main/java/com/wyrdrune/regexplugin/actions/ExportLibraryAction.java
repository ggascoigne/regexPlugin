package com.wyrdrune.regexplugin.actions;

import com.wyrdrune.regexplugin.RegexPanel;

public class ExportLibraryAction extends GenericAction {
  public ExportLibraryAction(final RegexPanel panel) {
    super(panel, "exportLibrary", panel.getIconCache().getIcon("save.png"));
  }

  public void perform() {
    LibraryActor.performExport();
  }
}
