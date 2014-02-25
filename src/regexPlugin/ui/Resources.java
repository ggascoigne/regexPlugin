package regexPlugin.ui;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Resources {
  protected static final Resources ms_inst = new Resources();

  private ResourceBundle m_bundle;

  public static Resources getInstance() {
    return ms_inst;
  }

  Resources() {
    m_bundle = ResourceBundle.getBundle("regexPlugin.ui.regex");
  }

  public String getString(final String resourceKey) {
    try {
      return m_bundle.getString(resourceKey);
    } catch (MissingResourceException e) {
      e.printStackTrace();
      return "!" + resourceKey + "!";
    }
  }

  public String getString(final String resourceKey, final Object[] values) {
    try {
      final String str = getString(resourceKey);
      final MessageFormat format = new MessageFormat(str);
      return format.format(values);
    } catch (MissingResourceException e) {
      e.printStackTrace();
      return "!" + resourceKey + "!";
    }
  }

  public int getInt(final String resourceKey, final int defaultValue) {
    try {
      return Integer.parseInt(getString(resourceKey));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return defaultValue;
  }

  public static String getLabel(final String resourceKey) {
    return getInstance().getString(resourceKey + ".label");
  }

  public static String getTitle(final String resourceKey) {
    return getInstance().getString(resourceKey + ".title");
  }

  public static String getTooltip(final String resourceKey) {
    return getInstance().getString(resourceKey + ".tooltip");
  }

  public static String getStatusTip(String resourceKey) {
    return getInstance().getString(resourceKey + ".statustip");
  }

}
