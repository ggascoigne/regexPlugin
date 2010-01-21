package regexPlugin.regexEditor;

import regexPlugin.MatchAction;
import regexPlugin.actions.Regex2JavaString;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.datatransfer.*;
import java.awt.im.InputContext;
import java.io.*;

public class EditorTransferHandler extends TransferHandler {
  private static final int BUF_SIZE = 1024;
  private MatchAction fAction;

  public EditorTransferHandler(String property, MatchAction action) {
    super(property);
    fAction = action;
  }

  public void exportToClipboard(JComponent comp, Clipboard clip, int action) {
    clip.setContents(new StringSelection(Regex2JavaString.quote(((RegexEditor) comp).getText(),
      fAction.getFlags())),
      null);
  }

  public boolean importData(JComponent comp, Transferable t) {
    if (comp instanceof JTextComponent) {
      final DataFlavor flavor = getFlavor(t);
      if (flavor != null) {
        final InputContext ic = comp.getInputContext();
        if (ic != null) {
          ic.endComposition();
        }
        ((JTextComponent) comp).replaceSelection(stripHtmlSpaces(extractTextFromFlavor(flavor, t)));
        return true;
      }
    }
    return false;
  }

  private String extractTextFromFlavor(final DataFlavor flavor, final Transferable t) {
    String text = "";
    try {
      if (flavor.isRepresentationClassInputStream()) {
        final InputStream is = (InputStream) t.getTransferData(flavor);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        final char[] ca = new char[is.available()];
        reader.read(ca, 0, is.available());
        text = new String(ca);
      } else if (flavor.isRepresentationClassReader()) {
        final Reader reader = (Reader) t.getTransferData(flavor);

        // read the source entirely into a bufer
        final StringWriter tmpWriter = new StringWriter(BUF_SIZE);
        final char[] buf = new char[BUF_SIZE];
        int read;
        while ((read = reader.read(buf)) >= 0) {
          tmpWriter.write(buf, 0, read);
        }
        text = tmpWriter.getBuffer().toString();
      } else if (flavor.isRepresentationClassCharBuffer() || flavor.isRepresentationClassByteBuffer() ||
        flavor.isRepresentationClassSerializable()) {
        text = (String) t.getTransferData(flavor);
      }
    } catch (UnsupportedFlavorException ufe) {
    } catch (IOException ioe) {
    }
    return text;
  }

  private String stripHtmlSpaces(String data) {
    final char[] chars = new char[data.length()];
    data.getChars(0, data.length(), chars, 0);
    for (int i = 0; i < chars.length; i++) {
      if (chars[i] == 160) // non-break space
        chars[i] = ' ';
    }
    return new String(chars, 0, chars.length);
  }

  private DataFlavor getFlavor(Transferable t) {
    final DataFlavor[] flavors = t.getTransferDataFlavors();
    if (flavors != null) {
      //System.out.println("flavor count = " + flavors.length);
      //for (int counter = 0; counter < flavors.length; counter++) {
      //  System.out.println("flavors[" + counter + "]" + flavors[counter]);
      //  System.out.println("text = " + extractTextFromFlavor(flavors[counter], t));
      //}
      for (int counter = 0; counter < flavors.length; counter++) {
        if (flavors[counter].equals(DataFlavor.stringFlavor)) {
          return flavors[counter];
        }
      }
    }
    return null;
  }

}