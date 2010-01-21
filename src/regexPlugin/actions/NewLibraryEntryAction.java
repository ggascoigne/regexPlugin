package regexPlugin.actions;

import regexPlugin.RegexPanel;
import regexPlugin.IconCache;
import regexPlugin.LibraryManager;
import regexPlugin.event.EventManager;

public class NewLibraryEntryAction extends GenericAction {
  public NewLibraryEntryAction(final RegexPanel panel) {
    super(panel, "newLibEntry", panel.getIconCache().getIcon("add.png"));
  }

  public void perform() {
    LibraryManager.newEntry();
  }
}
