package regexPlugin;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.DefaultJDOMExternalizer;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizable;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import regexPlugin.uiInterface.ComponentFactory;
import regexPlugin.uiInterface.ComponentManager;
import regexPlugin.uiInterface.Idea;

import javax.swing.*;
import java.awt.*;

public class RegexPlugin implements ProjectComponent, JDOMExternalizable
{

    private static final String REGEX_PLUGIN_ID = "Regex";

    private Project fProject;

    private RegexPanel fPanel;

    private RegexPluginConfig fConfig = new RegexPluginConfig();

    public RegexPlugin( final Project project )
    {
        fProject = project;
        UIManager.put( "TextArea.font", new Font( "Monospaced", Font.PLAIN, 11 ) );
    }

    public void projectOpened()
    {
        final ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(
            fProject );
        toolWindowManager.registerToolWindow( REGEX_PLUGIN_ID, fPanel,
            ToolWindowAnchor.BOTTOM );
    }

    public void projectClosed()
    {
        final ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(
            fProject );
        toolWindowManager.unregisterToolWindow( REGEX_PLUGIN_ID );
        fPanel.saveLibrary();
    }

    public String getComponentName()
    {
        return "Regex";
    }

    public void initComponent()
    {
        SwingUtilities.invokeLater( new Runnable()
        {
            public void run()
            {
                try
                {
                    fPanel = new RegexPanel( fConfig,  ComponentFactory.getInstance(ComponentFactory.Type.IDEA) );
                }
                catch ( Exception e )
                {
                    Utils.handleException( "error.panelStartup", e );
                }
            }
        } );
    }

    public void disposeComponent()
    {
        fPanel.disposeComponent();
    }

    public void readExternal( org.jdom.Element element ) throws InvalidDataException
    {
        fConfig.readConfig( element );
    }

    public void writeExternal( org.jdom.Element element ) throws WriteExternalException
    {
        if ( fPanel != null )
        {
            DefaultJDOMExternalizer.writeExternal( fPanel.getConfig(), element );
        }
    }

    /**
     * Only used for debugging
     *
     * @return Some string that identifies the current owner of the fPanel, be it fProject or binary.
     */
    public String getName()
    {
        return fProject.getName();
    }

}
