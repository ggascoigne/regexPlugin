package regexPlugin.actions;

import regexPlugin.LibraryManager;
import regexPlugin.RegexPanel;

public class DeleteCurrentLibraryEntryAction extends GenericAction {
  public DeleteCurrentLibraryEntryAction(final RegexPanel panel) {
    super(panel, "deleteCurrentLibEntry", null);
  }

  public void perform() {
    LibraryManager.delete( m_panel.getCurrentLibraryName() );
  }
}

