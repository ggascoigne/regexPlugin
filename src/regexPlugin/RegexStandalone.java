package regexPlugin;

import regexPlugin.ui.Resources;
import regexPlugin.uiInterface.Swing;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;
import javax.swing.plaf.ColorUIResource;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;

public class RegexStandalone {
  RegexPanel m_panel = null;
  static JFrame m_mainWin = null;
  Preferences m_prefs = Preferences.userRoot().node("regexPlugin/RegexStandalone");

  public RegexStandalone() {
    final Font labelFont = new Font("Tahoma", Font.PLAIN, 11);

    UIManager.put("TextArea.font", new Font("Monospaced", Font.PLAIN, 11));
    UIManager.put("CheckBox.font", labelFont);
    UIManager.put("Button.font", labelFont);
    UIManager.put("Menu.font", labelFont);
    UIManager.put("CheckBoxMenuItem.font", labelFont);
    UIManager.put("MenuItem.font", labelFont);
    UIManager.put("ComboBox.font", labelFont);
    UIManager.put("TitledBorder.font", labelFont);
    UIManager.put("TitledBorder.titleColor", new Color(10, 36, 106));
    UIManager.put("FileChooser.font", labelFont);
    UIManager.put("ToolTip.background", new ColorUIResource(255, 255, 231));
    UIManager.put("ToolTip.font", labelFont);
    UIManager.put("List.font", labelFont);
    UIManager.put("TextField.font", labelFont);
    UIManager.put("Label.font", labelFont);
  }

  public void show() {
    m_mainWin = new JFrame(Resources.getTitle("standAlone"));
    // make sure that ALL swing related actions start from the event dispatcher thread.
    // stops a race condition (NPE) upon startup.
    SwingUtilities.invokeLater( new Runnable() {
      public void run() {
        try {
          m_panel = new RegexPanel(getConfig(), new Swing());
          m_mainWin.getContentPane().add(m_panel);
          synchronized (LibraryManager.m_Lock) {
            m_mainWin.pack();
            m_mainWin.setBounds(m_prefs.getInt("x", 10),
              m_prefs.getInt("y", 10),
              m_prefs.getInt("width", 933),
              m_prefs.getInt("height", 428));
            m_mainWin.setVisible(true);
            m_panel.initializeDividerPositions( m_prefs.getInt( "pos1", 0) == 0 );
          }
          m_mainWin.addWindowListener(
            new WindowAdapter() {
              public void windowClosing(final WindowEvent e) {
                saveConfig();
                m_panel.saveLibrary();
                System.exit(0);
              }
            });
        } catch (Exception e) {
          Utils.handleException("error.panelStartup",e);
        }
      }
    } );
  }

  private void saveConfig() {
    m_panel.getConfig().saveConfig(m_prefs);

    m_prefs.putInt("x", m_mainWin.getX());
    m_prefs.putInt("y", m_mainWin.getY());
    m_prefs.putInt("width", m_mainWin.getWidth());
    m_prefs.putInt("height", m_mainWin.getHeight());
  }

  public RegexPluginConfig getConfig() {
    final RegexPluginConfig config = new RegexPluginConfig();
    config.loadConfig(m_prefs);
    return config;
  }

  public static void main(final String[] args) {
    new RegexStandalone().show();
  }

  /**
   * Only used for debugging
   *
   * @return Some string that identifies the current owner of the panel, be it project or binary.
   */
  public String getName() {
    return "Standalone";
  }

}
