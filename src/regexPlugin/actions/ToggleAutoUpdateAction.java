package regexPlugin.actions;

import regexPlugin.IconCache;
import regexPlugin.MatchAction;
import regexPlugin.RegexPluginConfig;

public class ToggleAutoUpdateAction extends MatchToggleAction
{

    public ToggleAutoUpdateAction( MatchAction action, IconCache iconCache,
        RegexPluginConfig config )
    {
        super( null, "autoUpdate", iconCache.getIcon( "autoupdate.png" ), action,
            config );
    }

    public boolean isSelected()
    {
        return fConfig.autoUpdate;
    }

    public void setSelected( boolean b )
    {
        fConfig.autoUpdate = b;
        if ( b == true )
        {
            fireAction( true );
        }
    }

}
