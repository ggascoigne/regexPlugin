/*
 * Copyright (c) 2004 Your Corporation. All Rights Reserved.
 */
package regexPlugin.actions;

import regexPlugin.RegexPanel;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.io.IOException;

public class ShowHtmlAction extends GenericAction {
  private final String fDocument;

  public ShowHtmlAction(final RegexPanel panel, final String name, final String document) {
    super(panel, name, null);
    fDocument = document;
  }

  public void perform() {
    final JFrame aboutDialog = new JFrame();
    aboutDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    final JEditorPane html = new JEditorPane();
    html.setAutoscrolls(true);
    html.setEditable(false);
    html.setEditorKit(new HTMLEditorKit());
    html.addHyperlinkListener(new HyperlinkListener() {
      public void hyperlinkUpdate(HyperlinkEvent e) {
        if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
          try {
            html.setPage(e.getURL());
          } catch (IOException e1) {
            html.setText(e1.toString());
          }
        }
      }
    });
    JScrollPane scroller = new JScrollPane(html);
    aboutDialog.getContentPane().add(scroller, BorderLayout.CENTER);
    try {
      html.setPage(getClass().getResource(fDocument));
    } catch (IOException e1) {
      html.setText(e1.toString());
    }

    center(aboutDialog, 550, 200);
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        aboutDialog.setVisible(true);
        aboutDialog.toFront();
      }
    });
  }

  private void center(Component c, int width, int height) {
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    c.setBounds((screen.width - width) / 2, (screen.height - height) / 2, width, height);
  }
}
