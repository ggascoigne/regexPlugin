package com.wyrdrune.regexplugin.actions;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.JTextComponent;

import com.wyrdrune.regexplugin.RegexPanel;
import com.wyrdrune.regexplugin.Utils;
import com.wyrdrune.regexplugin.ui.Resources;

public class ImportTextAction extends GenericAction {

  private final JTextComponent text;

  public ImportTextAction(final RegexPanel panel) {
    super(panel, "ImportTextAction.chooseTextImportFile",
        panel.getIconCache().getIcon("open.png"));
    this.text = panel.getTextComponent();
  }

  public void perform() {
//    sad ... would like to have a cool intellij filechooser .. whats the difference???
//    FileChooser fC = new FileChooser(new FileChooserDescriptor(true, false, false, false, true, false), getParent());
//    VirtualFile[] help = fC.getChosenFiles();
//    for (int i = 0; i < help.length; i++) {
//      VirtualFile virtualFile = help[i];
//      System.out.println("virtualFile = " + virtualFile);
//    }

    final JFileChooser chooser = new JFileChooser();
    chooser.setDialogTitle(
        Resources.getLabel("ImportTextAction.chooseTextImportFile"));
    chooser.setDialogType(JFileChooser.OPEN_DIALOG);
    chooser.setMultiSelectionEnabled(false);

    chooser.setFileFilter(new FileFilter() {
      public boolean accept(File f) {
        return f.isFile();
      }

      public String getDescription() {
        return Resources.getLabel("ImportTextAction.filesOnly");
      }
    });
    final int returnVal = chooser.showDialog(null,
        Resources.getLabel("importDialog"));
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File f = chooser.getSelectedFile();
      try {
        text.setText(getText(f));
      } catch (IOException e) {
        Utils.handleException("error.importingLibrary", e);
      }
    }
  }

  private String getText(File f) throws IOException {
    StringBuffer all = new StringBuffer();
    char[] buffer = new char[2048];
    Reader r = new FileReader(f);

    int charsRead = r.read(buffer);
    while (charsRead != -1) {
      all.append(buffer, 0, charsRead);
      charsRead = r.read(buffer);
    }

    return all.toString();
  }

}
