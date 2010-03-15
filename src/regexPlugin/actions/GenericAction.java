package regexPlugin.actions;

import regexPlugin.RegexPanel;
import regexPlugin.ui.Resources;

import javax.swing.*;

public abstract class GenericAction
{
    protected RegexPanel m_panel;

    private String m_key;

    private Icon m_icon;

    public GenericAction( final RegexPanel panel, final String key, final Icon icon )
    {
        m_panel = panel;
        m_key = key;
        m_icon = icon;
    }

    public abstract void perform();

    public String getName()
    {
        return Resources.getLabel( m_key );
    }

    public String getDescription()
    {
        return Resources.getTooltip( m_key );
    }

    public Icon getIcon()
    {
        return m_icon;
    }

}
