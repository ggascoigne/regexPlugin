package regexPlugin.actions;

import regexPlugin.RegexPanel;
import regexPlugin.event.EventManager;

public class ToggleLabelsAction extends GenericToggleAction
{

    public ToggleLabelsAction( final RegexPanel panel )
    {
        super( panel, "showLabels", null );
    }

    public boolean isSelected()
    {
        return m_panel.getConfig().showLabels;
    }

    public void setSelected( boolean b )
    {
        EventManager.getInstance()
            .fireEvent( EventManager.getGlobalTarget(), RegexPanel.EVENT_SHOW_LABELS,
                new Boolean( b ) );
    }

    public void perform()
    {
        //not used
    }
}
