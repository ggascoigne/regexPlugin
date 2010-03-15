package regexPlugin.uiInterface;

import regexPlugin.RegexPanel;
import regexPlugin.actions.GenericAction;
import regexPlugin.actions.GenericToggleAction;
import regexPlugin.ui.MenuEx;
import regexPlugin.ui.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;

public class Swing extends ComponentFactory
{

    static final int ICON_SIZE = 24;

    public Object createGroup( String name )
    {
        JPanel res = new JPanel( new FlowLayout( FlowLayout.LEFT, 0, 0 ) );
        return res;
    }

    public Object createMenuAction( RegexPanel panel, Icon i, JPopupMenu popup )
    {
        return createCoolButton(
            new MenuAction( popup, createIconWithBorder( i, ICON_SIZE ) ) );
    }

    public Object createToggleAction( GenericToggleAction a )
    {
        return new ToggleButton( a );
    }

    class ToggleMenuItem extends JCheckBoxMenuItem implements ItemListener
    {
        final private GenericToggleAction fAction;

        public ToggleMenuItem( GenericToggleAction action )
        {
            super( new GenericAction2AbstractAction( action ) );
            fAction = action;
            setToolTipText( action.getDescription() );
            addItemListener( this );
            setState( action.isSelected() );
        }

        public void itemStateChanged( ItemEvent e )
        {
            fAction.setSelected( isSelected() );
        }
    }

    public Object createToggleMenuAction( final GenericToggleAction action )
    {
        return new ToggleMenuItem( action );
    }

    public void addToGroup( Object group, Object something )
    {
        JPanel panel = (JPanel)group;
        panel.add( (JComponent)something );
    }

    public Object createGenericActionMenuAdapter( GenericAction action )
    {
        return new JMenuItem( new GenericAction2AbstractAction( action ) );
    }

    private JButton createCoolButton( final AbstractAction action )
    {
        JButton res = new JButton( action );
        res.setBorder( null );
        res.setText( null );
        return res;
    }

    public Object createGenericActionAdapter( GenericAction action )
    {
        return createCoolButton( new GenericAction2AbstractAction( action ) );
    }

    private Icon createIconWithBorder( Icon icon, int iconSize )
    {
        if ( icon == null ) return null;

        int iconWidth = icon.getIconWidth();
        int iconHeight = icon.getIconHeight();

        BufferedImage i = new BufferedImage( iconSize, iconSize,
            BufferedImage.TYPE_INT_ARGB );
        Graphics g = i.getGraphics();
        icon.paintIcon( null, g, (iconSize - iconWidth) / 2,
            (iconSize - iconHeight) / 2 );
        return new ImageIcon( i );
    }

    public JComponent getComponent( String name, Object group, boolean horizontal )
    {
        return (JComponent)group;
    }

    protected Object createSubMenu( String name, List actions )
    {
        JMenu editMenu = new MenuEx( name );
        addToMenu( editMenu, actions );
        return editMenu;
    }

    protected JPopupMenu createPopup( String key, List menuEntries )
    {
        final JPopupMenu res = new JPopupMenu( key );
        for ( Iterator iterator = menuEntries.iterator(); iterator.hasNext(); )
        {
            JComponent component = (JComponent)iterator.next();
            res.add( component );
        }
        return res;
    }

    private void addToMenu( JComponent menu, List actions )
    {
        for ( Iterator iterator = actions.iterator(); iterator.hasNext(); )
        {
            GenericAction action = (GenericAction)iterator.next();
            menu.add( (JComponent)createGenericActionMenuAdapter( action ) );
        }
    }

    class GenericAction2AbstractAction extends AbstractAction
    {
        private GenericAction a;

        public GenericAction2AbstractAction( GenericAction a )
        {
            super( a.getName(), createIconWithBorder( a.getIcon(), ICON_SIZE ) );
            this.a = a;
            putValue( SHORT_DESCRIPTION, a.getDescription() );
        }

        public void actionPerformed( ActionEvent e )
        {
            a.perform();
        }
    }

    class CheckboxToggleButton extends JCheckBox implements ItemListener
    {
        final private GenericToggleAction fAction;

        public CheckboxToggleButton( GenericToggleAction action )
        {
            super( action.getName(), action.isSelected() );
            fAction = action;
            setToolTipText( action.getDescription() );
            addItemListener( this );
        }

        public void itemStateChanged( ItemEvent e )
        {
            fAction.setSelected( isSelected() );
        }
    }

    class ToggleButton extends JCheckBox implements ItemListener
    {
        private GenericToggleAction action;

        public ToggleButton( GenericToggleAction a )
        {
            super( a.getIcon(), a.isSelected() );
            action = a;

            Icon normalIcon = createIconWithBorder( a.getIcon(), ICON_SIZE );
            setIcon( normalIcon );
            setMargin( new Insets( 0, 0, 0, 0 ) );

            Icon rolloverIcon = createAnotherIcon( normalIcon,
                new Color( 181, 190, 214 ) );
            setRolloverIcon( rolloverIcon );
            setRolloverSelectedIcon( rolloverIcon );
            setSelectedIcon(
                createAnotherIcon( normalIcon, new Color( 130, 146, 185 ) ) );

            if ( action.showDescription() )
            {
                setText( a.getName() );
                // if we're going to use the name on the button then we need to get a border otherwise
                // the toolbar gets very hard to read.
                setBorderPainted( true );
            }
            setToolTipText( a.getDescription() );

            addItemListener( this );
        }

        //public Dimension getMinimumSize() {
        //  Dimension minimumSize = super.getMinimumSize();
        //  return new Dimension(minimumSize.width + 6, minimumSize.height + 6);
        //}

        private Icon createAnotherIcon( Icon icon, Color color )
        {
            if ( icon == null ) return null;
            int iconWidth = icon.getIconWidth();
            int iconHeight = icon.getIconHeight();
            BufferedImage i = new BufferedImage( iconWidth, iconHeight,
                BufferedImage.TYPE_INT_ARGB );
            Graphics g = i.getGraphics();
            g.setColor( color );
            g.fillRect( 0, 0, iconWidth, iconHeight );
            g.setColor( (Color.BLACK) );
            g.drawRect( 0, 0, iconWidth - 1, iconHeight - 1 );
            icon.paintIcon( null, g, 0, 0 );
            return new ImageIcon( i );
        }

        public void itemStateChanged( ItemEvent e )
        {
            action.setSelected( isSelected() );
        }

    }

    private class MenuAction extends AbstractAction
    {
        private JPopupMenu menu;

        public MenuAction( JPopupMenu menu, Icon i )
        {
            super( Resources.getLabel( "menu" ), i );
            putValue( SHORT_DESCRIPTION, Resources.getTooltip( "menu" ) );
            this.menu = menu;
        }

        public void actionPerformed( ActionEvent e )
        {
            menu.show( (Component)e.getSource(), 0, 0 );
        }
    }

}
