package regexPlugin.actions;

import regexPlugin.RegexPanel;

import javax.swing.*;

public abstract class GenericToggleAction extends GenericAction
{
    protected GenericToggleAction( final RegexPanel panel, final String key,
        final Icon icon )
    {
        super( panel, key, icon );
    }

    public boolean showDescription()
    {
        return false;
    }

    public abstract boolean isSelected();

    public abstract void setSelected( boolean b );

}
