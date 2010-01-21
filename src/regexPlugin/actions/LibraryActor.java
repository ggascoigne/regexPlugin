package regexPlugin.actions;

import regexPlugin.ExampleFileFilter;
import regexPlugin.LibraryManager;
import regexPlugin.Utils;
import regexPlugin.ui.Resources;

import javax.swing.*;
import java.io.IOException;

public class LibraryActor {

  public static void performExport() {
    final JFileChooser chooser = getXmlFileChooser("exportLibrary", true);
    final int returnVal = chooser.showDialog(null, Resources.getLabel("exportDialog"));
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      try {
        final String fileName = chooser.getSelectedFile().getCanonicalPath();
        if (fileName != null) {
          LibraryManager.exportFile(fileName);
        }
      } catch (IOException e) {
        Utils.handleException("error.exportingLibrary", e);
      }
    }
  }

  public static void performImport() {
    final JFileChooser chooser = getXmlFileChooser("importLibrary", false);
    final int returnVal = chooser.showDialog(null, Resources.getLabel("importDialog"));
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      try {
        final String fileName = chooser.getSelectedFile().getCanonicalPath();
        if (fileName != null) {
          LibraryManager.importFile(fileName);
        }
      } catch (IOException e) {
        Utils.handleException("error.importingLibrary", e);
      }
    }
  }

  private static JFileChooser getXmlFileChooser(final String title, final boolean saving) {
    final JFileChooser chooser = new JFileChooser();
    chooser.setDialogTitle(Resources.getLabel(title));
    chooser.setDialogType(saving ? JFileChooser.SAVE_DIALOG : JFileChooser.OPEN_DIALOG);

    final ExampleFileFilter filter = new ExampleFileFilter();
    filter.addExtension("xml");
    filter.setDescription(Resources.getLabel("xmlFiles"));
    chooser.setFileFilter(filter);
    return chooser;
  }
}
