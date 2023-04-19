package com.timindustries.regexpluginv2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import com.timindustries.regexpluginv2.library.LibraryComboBoxModel;
import com.timindustries.regexpluginv2.event.EventManager;
import com.timindustries.regexpluginv2.event.Notifiable;

public class LibraryManager implements ActionListener, PopupMenuListener, Notifiable {
  static final String libraryRoot = "regexPluginV2/Library";

  public static final String EVENT_NEW = "new";

  public static final String EVENT_SAVE = "save";

  public static final String EVENT_LOAD = "load";

  public static final String EVENT_DELETE = "delete";

  public static final String EVENT_IMPORT = "import";

  private RegexPanel m_panel;

  private String m_currentLibraryName;

  private static boolean m_inUpdate = false;

  private JComboBox m_comboBox;

  private static final String DEFAULT_LIBRARY_NAME = "Current Regex";

  public static final Object m_Lock = new Object();

  public LibraryManager() {
  }

  public void init(final RegexPanel panel) {
    m_panel = panel;
  }

  public void actionPerformed(final ActionEvent e) {
    final JComboBox cb = (JComboBox) e.getSource();
    if (cb == null || m_comboBox == null) {
      m_comboBox = cb;
    } else {
      assert cb == m_comboBox;
    }

    final String newLibraryName = (String) cb.getSelectedItem();

    //System.out.println(
    //  m_panel.getName() + " listener " + getCurrentLibraryName()
    //  + " / " + newLibraryName + " / " + e.getActionCommand() + " " + m_inUpdate);

    if (!m_inUpdate) {
      if ("comboBoxChanged".equals(e.getActionCommand()) &&
          !getCurrentLibraryName().equals(newLibraryName)) {
        // A new Selection has been made
        if (findItem(cb, newLibraryName) == -1) {
          addToList(cb, newLibraryName);
        } else {
          saveLibrary(getCurrentLibraryName());
          loadLibrary(newLibraryName);
        }
      } else if ("comboBoxEdited".equals(e.getActionCommand())) {
        // return hit in control
        saveLibrary(getCurrentLibraryName());
        if (findItem(cb, newLibraryName) == -1) {
          addToList(cb, newLibraryName);
        } else {
          loadLibrary(newLibraryName);
        }
      }
      setCurrentLibraryName(newLibraryName);
    }
  }

  public void popupMenuWillBecomeVisible(final PopupMenuEvent e) {
    final JComboBox cb = (JComboBox) e.getSource();
    if (cb == null || m_comboBox == null) {
      m_comboBox = cb;
    } else {
      assert cb == m_comboBox;
    }
    final String currentName = (String) cb.getSelectedItem();
    final int rowCount = cb.getMaximumRowCount();
    loadModel(m_panel.getLibraryModel());
    cb.setSelectedItem(currentName);
    cb.setMaximumRowCount(rowCount);
  }

  public void popupMenuCanceled(final PopupMenuEvent e) {
  }

  public void popupMenuWillBecomeInvisible(final PopupMenuEvent e) {
  }

  private void addToList(final JComboBox cb, final String libraryName) {
    cb.addItem(libraryName);
  }

  private int findItem(final JComboBox cb, final String name) {
    for (int i = 0; i < cb.getItemCount(); i++) {
      if (name.equals(cb.getItemAt(i))) {
        return i;
      }
    }
    return -1;
  }

  // There is a bug in the Preferences exportXML api

  private String pack(final String s) {
    final int len = s.length();
    final StringBuffer ret = new StringBuffer(len + 16);
    for (int i = 0; i < len; i++) {
      final char c = s.charAt(i);
      if (c == '\n') {
        ret.append("\\n");
      } else if (c == '\\') {
        ret.append("\\\\");
      } else {
        ret.append(c);
      }
    }
    return ret.toString();
  }

  private String unpack(final String s) {
    final int len = s.length();
    final StringBuffer ret = new StringBuffer(len);
    for (int i = 0; i < len; i++) {
      char c = s.charAt(i);
      if (c == '\\') {
        i++;
        if (i < len) {
          c = s.charAt(i);
          if (c == 'n') {
            ret.append('\n');
          } else if (c == '\\') {
            ret.append('\\');
          } else {
            throw new Error("wrong format");
          }
        } else {
          throw new Error("wrong format");
        }
      } else {
        ret.append(c);
      }
    }
    return ret.toString();
  }

  // work around a bug in Preferences.exportSubtree API, by default it kills all new lines
  // so we wrap all of them and unpack them later.

  public void loadLibrary(String libraryName) {
    if (libraryName == null || libraryName.length() == 0) {
      libraryName = DEFAULT_LIBRARY_NAME;
      setCurrentLibraryName(DEFAULT_LIBRARY_NAME);
    }
    final String lName = libraryName;
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        synchronized (m_Lock) {
          //System.out.println(m_panel.getName() + " Loading " + libraryName);
          final Preferences prefs = Preferences.userRoot()
              .node(libraryRoot + '/' + lName);
          final String patternText = unpack(prefs.get("pattern", ""));
          final String textText = unpack(prefs.get("text", ""));
          final String replaceText = unpack(prefs.get("replace", ""));

          m_panel.clearFields();
          m_panel.getPatternComponent().setText(patternText);
          m_panel.getTextComponent().setText(textText);
          m_panel.getReplaceInputComponent().setText(replaceText);
          m_panel.setFlags(prefs.getInt("flags", 0));
        }
      }
    });
  }

  public void saveLibrary(String libraryName) {
    if (libraryName == null || libraryName.length() == 0) {
      libraryName = DEFAULT_LIBRARY_NAME;
      setCurrentLibraryName(DEFAULT_LIBRARY_NAME);
    }

    final Preferences prefs = Preferences.userRoot()
        .node(libraryRoot + '/' + libraryName);
    prefs.put("pattern", pack(m_panel.getPatternComponent().getText()));
    prefs.put("text", pack(m_panel.getTextComponent().getText()));
    prefs.put("replace", pack(m_panel.getReplaceInputComponent().getText()));

    prefs.putInt("flags", m_panel.getFlags());

    EventManager.getInstance()
        .fireEvent(this, LibraryManager.EVENT_SAVE, libraryName);
  }

  public void saveCurrent() {
    final Preferences prefs = Preferences.userRoot().node(libraryRoot);
    prefs.put("current", getCurrentLibraryName());
    saveLibrary(getCurrentLibraryName());
  }

  public void loadCurrent() {
    final Preferences prefs = Preferences.userRoot().node(libraryRoot);
    setCurrentLibraryName(prefs.get("current", getCurrentLibraryName()));
    loadLibrary(getCurrentLibraryName());
  }

  public String[] getLibraryList() {
    if (getCurrentLibraryName() == null) {
      setCurrentLibraryName(DEFAULT_LIBRARY_NAME);
    }
    final Preferences prefs = Preferences.userRoot().node(libraryRoot);
    try {
      String[] ret = prefs.childrenNames();
      if (ret.length == 0) {
        ret = new String[]{getCurrentLibraryName()};
      }
      return ret;
    } catch (BackingStoreException e) {
      return new String[]{getCurrentLibraryName()};
    }
  }

  public String getCurrentLibraryName() {
//    System.out.println( "LibraryManager.getCurrentLibraryName : " + m_currentLibraryName );
    return m_currentLibraryName;
  }

  public void setCurrentLibraryName(final String currentLibraryName) {
//    System.out.println( "LibraryManager.setCurrentLibraryName : " + currentLibraryName );
    m_currentLibraryName = currentLibraryName;
  }

  public synchronized void loadModel(final LibraryComboBoxModel libraryModel) {
    m_inUpdate = true;
    final String[] names = getLibraryList();
    libraryModel.replaceAllElements(names);
    m_inUpdate = false;
  }

  public static void delete(final String libraryName) {
//    System.out.println("Delete " + libraryName);
    final Preferences prefs = Preferences.userRoot()
        .node(libraryRoot + '/' + libraryName);
    try {
      prefs.removeNode();
      EventManager.getInstance()
          .fireEvent(EventManager.getGlobalTarget(), LibraryManager.EVENT_DELETE,
              libraryName);
    } catch (BackingStoreException e) {
      Utils.handleException("error.libraryDelete", e);
    }
  }

  public static void newEntry() {
    System.out.println("New");
    EventManager.getInstance()
        .fireEvent(EventManager.getGlobalTarget(), LibraryManager.EVENT_NEW, null);
  }

  /**
   * Called in response to the static delete method
   *
   * @param libraryName
   */
  public void deleted(final String libraryName) {
//    System.out.println( "lib" + libraryName + ":" + getCurrentLibraryName() );
    if (libraryName.equals(getCurrentLibraryName())) {
      final String[] names = getLibraryList();
      loadModel(m_panel.getLibraryModel());
      m_panel.clearFields();
      final String newLibraryName = names != null ? names[0] : null;
      m_panel.getLibraryModel().setSelectedItem(newLibraryName);
      loadLibrary(newLibraryName);
      setCurrentLibraryName(newLibraryName);
    }
  }

  /**
   * Called in response to the static newEntry method
   */
  public void newed() {
    final String[] names = getLibraryList();
    loadModel(m_panel.getLibraryModel());
    m_panel.clearFields();
    final String newLibraryName = makeNewLibraryName(names);
    m_panel.getLibraryModel().setSelectedItem(newLibraryName);
    loadLibrary(newLibraryName);
    setCurrentLibraryName(newLibraryName);
  }

  private boolean find(String[] names, String name) {
    for (int i = 0; i < names.length; i++) {
      String s = names[i];
      if (s.equals(name)) {
        return true;
      }
    }
    return false;
  }

  private String makeNewLibraryName(String[] names) {
    final String initialLibraryName = "New Regex";
    String newLibraryName = initialLibraryName;
    int i = 0;
    while (find(names, newLibraryName)) {
      newLibraryName = initialLibraryName + " " + ++i;
    }
    return newLibraryName;
  }

  /**
   * Called in response to the static import method
   */
  public void imported() {
    final Preferences prefs = Preferences.userRoot().node(libraryRoot);
    try {
      prefs.flush();
    } catch (BackingStoreException e) {
      Utils.handleException("error.importingLibrary", e);
    }
    loadModel(m_panel.getLibraryModel());
  }

  public static void importFile(final String fileName) {
    final File f = new File(fileName);
    try {
      final InputStream is = new FileInputStream(f);
      Preferences.importPreferences(is);
      is.close();
      EventManager.getInstance()
          .fireEvent(EventManager.getGlobalTarget(), LibraryManager.EVENT_IMPORT,
              null);
    } catch (Exception e) {
      Utils.handleException("error.importingLibrary", e);
    }
  }

  public static void exportFile(final String fileName) {
    final File f = new File(fileName);
    try {
      final OutputStream os = new FileOutputStream(f);
      final Preferences prefs = Preferences.userRoot().node(libraryRoot);
      prefs.exportSubtree(os);
      os.close();
    } catch (Exception e) {
      Utils.handleException("error.exportingLibrary", e);
    }
  }

}
