package regexPlugin.actions;

import regexPlugin.RegexPanel;

public class ExportLibraryAction extends GenericAction {
  public ExportLibraryAction(final RegexPanel panel) {
    super(panel, "exportLibrary", null);
  }

  public void perform() {
    LibraryActor.performExport();
  }
}
