package regexPlugin.actions;

import regexPlugin.LibraryManager;
import regexPlugin.RegexPanel;

public class NewLibraryEntryAction extends GenericAction
{
    public NewLibraryEntryAction( final RegexPanel panel )
    {
        super( panel, "newLibEntry", panel.getIconCache().getIcon( "add.png" ) );
    }

    public void perform()
    {
        LibraryManager.newEntry();
    }
}
