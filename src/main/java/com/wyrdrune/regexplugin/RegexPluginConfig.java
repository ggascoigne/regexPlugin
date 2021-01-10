package com.wyrdrune.regexplugin;

import java.awt.*;
import java.util.Iterator;
import java.util.prefs.Preferences;

import org.jdom.Element;

public class RegexPluginConfig {
  private boolean initialized = false;

  public int pos1 = 218;

  public int pos2 = 218;

  public int pos3 = 162;

  public int pos4 = 444;

  public int pos5 = 162;

  public boolean autoUpdate = true;

  public boolean referenceOn = false;

  public int referencePos = 50;

  public boolean showLabels = true;

  public RegexPluginConfig() {
  }

  public RegexPluginConfig(final int pos1, final int pos2, final int pos3,
      final int pos4, final int pos5, final boolean autoUpdate,
      final boolean referenceOnOff, final int referencePos,
      final boolean showLabels) {
    update(pos1, pos2, pos3, pos4, pos5, autoUpdate, referenceOnOff, referencePos,
        showLabels);
  }

  public void update(int pos1, int pos2, int pos3, int pos4, int pos5,
      boolean autoUpdate, boolean referenceOnOff, int referencePos,
      boolean showLabels) {
    this.pos1 = pos1;
    this.pos2 = pos2;
    this.pos3 = pos3;
    this.pos4 = pos4;
    this.pos5 = pos5;
    this.autoUpdate = autoUpdate;
    this.referenceOn = referenceOnOff;
    this.referencePos = referencePos;
    this.showLabels = showLabels;
  }

  public String toString() {
    return "RegexPluginConfig:\n" + " pos1: " + pos1 + "\n" + " pos2: " + pos2 +
        "\n" + " pos3: " + pos3 + "\n" + " pos4: " + pos4 + "\n" + " pos5: " + pos5 +
        "\n" + " autoUpdate: " + autoUpdate + "\n" + " referenceOn: " + referenceOn +
        "\n" + " referencePos: " + referencePos + "\n" + " showLabels: " +
        showLabels + "\n";
  }

  /**
   * read the plugins configuration from IntelliJ's user preferences
   *
   * @param e the JDOM element containing the attributes that we are interested in
   */
  public void readConfig(final Element e) {
    pos1 = readIntOption(e, "pos1", pos1);
    pos2 = readIntOption(e, "pos2", pos2);
    pos3 = readIntOption(e, "pos3", pos3);
    pos4 = readIntOption(e, "pos4", pos4);
    pos5 = readIntOption(e, "pos5", pos5);

    autoUpdate = readBooleanOption(e, "autoUpdate", autoUpdate);
    referenceOn = readBooleanOption(e, "referenceOn", referenceOn);
    referencePos = readIntOption(e, "referencePos", referencePos);
    showLabels = readBooleanOption(e, "showLabels", showLabels);
//    System.out.println("RegexPluginConfig.readConfig " + this);

    // simple sanity check
    if (referencePos > pos1) {
      initialized = true;
    }
  }

  private int readIntOption(final Element e, final String name,
      final int defaultValue) {
    Iterator i = e.getChildren("option").iterator();
    while (i.hasNext()) {
      final Element option = (Element) i.next();
      if (option.getAttributeValue("name").equals(name)) {
        return Integer.parseInt(option.getAttributeValue("value"));
      }
    }
    return defaultValue;
  }

  private boolean readBooleanOption(final Element e, final String name,
      final boolean defaultValue) {
    Iterator i = e.getChildren("option").iterator();
    while (i.hasNext()) {
      final Element option = (Element) i.next();
      if (option.getAttributeValue("name").equals(name)) {
        return Boolean.parseBoolean(option.getAttributeValue("value"));
      }
    }
    return defaultValue;
  }

  /**
   * Save the plugins splitter positions to a Java Preferences object (Used by the stand alone version)
   *
   * @param prefs
   */
  public void saveConfig(final Preferences prefs) {
    try {
      prefs.putInt("pos1", pos1);
      prefs.putInt("pos2", pos2);
      prefs.putInt("pos3", pos3);
      prefs.putInt("pos4", pos4);
      prefs.putInt("pos5", pos5);
      prefs.putBoolean("autoUpdate", autoUpdate);
      prefs.putBoolean("referenceOn", referenceOn);
      prefs.putInt("referencePos", referencePos);
      prefs.putBoolean("showLabels", showLabels);
    } catch (Exception e) {
      Utils.handleException("error.savingPreferences", e);
    }
  }

  /**
   * Read the plugins splitter positions from a Java Preferences object (Used by the stand alone version)
   *
   * @param prefs
   */
  public void loadConfig(final Preferences prefs) {
    try {
      pos1 = prefs.getInt("pos1", pos1);
      pos2 = prefs.getInt("pos2", pos2);
      pos3 = prefs.getInt("pos3", pos3);
      pos4 = prefs.getInt("pos4", pos4);
      pos5 = prefs.getInt("pos5", pos5);
      autoUpdate = prefs.getBoolean("autoUpdate", autoUpdate);
      referenceOn = prefs.getBoolean("referenceOn", referenceOn);
      referencePos = prefs.getInt("referencePos", referencePos);
      showLabels = prefs.getBoolean("showLabels", showLabels);
      // simple sanity check
      if (referencePos > pos1) {
        initialized = true;
      }
    } catch (Exception e) {
      Utils.handleException("error.loadingPreferences", e);
    }
  }

  public boolean isAutoUpdateEnabled() {
    return autoUpdate;
  }

  public boolean initializeSize(Dimension size, boolean force) {
    if (!initialized || force) {
      if (size.width != 0 && size.height != 0) {
        pos1 = pos2 = size.width / 3;
        pos3 = pos5 = size.height / 2;
        pos4 = pos1 * 2;
        referencePos = pos4 + (size.width / 6);
        initialized = true;
        return true;
      }
    }
    return false;
  }
}
