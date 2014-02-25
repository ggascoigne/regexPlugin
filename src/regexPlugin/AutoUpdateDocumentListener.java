package regexPlugin;

import regexPlugin.actions.GenericAction;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

class AutoUpdateDocumentListener implements DocumentListener {

  private GenericAction anAction;

  private RegexPluginConfig config;

  public AutoUpdateDocumentListener(final GenericAction anAction,
      final RegexPluginConfig config) {
    this.anAction = anAction;
    this.config = config;
  }

  public void changedUpdate(final DocumentEvent e) {
    autoupdate();
  }

  public void insertUpdate(final DocumentEvent e) {
    autoupdate();
  }

  public void removeUpdate(final DocumentEvent e) {
    autoupdate();
  }

  private void autoupdate() {
    if (config.isAutoUpdateEnabled() == true) {
      anAction.perform();
    }
  }
}
