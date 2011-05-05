package regexPlugin.actions;

import regexPlugin.LibraryManager;
import regexPlugin.RegexPanel;

public class DeleteCurrentLibraryEntryAction extends GenericAction
{
    public DeleteCurrentLibraryEntryAction( final RegexPanel panel )
    {
        super( panel, "deleteCurrentLibEntry",
            panel.getIconCache().getIcon( "delete.png" ) );
    }

    public void perform()
    {
        LibraryManager.delete( m_panel.getCurrentLibraryName() );
    }
}

