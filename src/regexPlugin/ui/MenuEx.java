package regexPlugin.ui;

import javax.swing.Action;
import javax.swing.JMenu;

public class MenuEx extends JMenu {
  public MenuEx(final String resourceKey) {
    this(resourceKey, null);
  }

  public MenuEx(final String resourceKey, final Action a) {
    if (a != null)
      setAction(a);
    setText(Resources.getLabel(resourceKey));
  }
}
