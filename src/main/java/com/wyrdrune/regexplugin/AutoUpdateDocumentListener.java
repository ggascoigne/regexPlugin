package com.wyrdrune.regexplugin;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.wyrdrune.regexplugin.actions.GenericAction;

class AutoUpdateDocumentListener implements DocumentListener {

  private final GenericAction anAction;

  private final RegexPluginConfig config;

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
    if (config.isAutoUpdateEnabled()) {
      anAction.perform();
    }
  }
}
