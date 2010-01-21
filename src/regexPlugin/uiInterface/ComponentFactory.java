package regexPlugin.uiInterface;

import regexPlugin.RegexPanel;
import regexPlugin.actions.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public abstract class ComponentFactory {

  public JPopupMenu createRegexPopupMenu(RegexPanel panel) {
    List entries = new ArrayList();

    List actions = new ArrayList();
    actions.add(new CopyRegexStringAction(panel));
    actions.add(new PasteRegexStringAction(panel));
    actions.add(new ImportTextAction(panel));
    entries.add(createSubMenu("editMenu", actions));

    actions.clear();
    actions.add(new NewLibraryEntryAction(panel));
    actions.add(new DeleteCurrentLibraryEntryAction(panel));
    actions.add(new ImportLibraryAction(panel));
    actions.add(new ExportLibraryAction(panel));
    entries.add(createSubMenu("libMenu", actions));

    entries.add( createToggleMenuAction( new ToggleLabelsAction(panel)));

    entries.add(createGenericActionMenuAdapter(new ShowHtmlAction(panel, "help", "/help/help.html")));
    entries.add(createGenericActionMenuAdapter(new ShowHtmlAction(panel, "about", "/help/About.html")));

    return createPopup("RegexPopup", entries);
  }

  protected abstract Object createSubMenu(String key, List actions);

  public abstract Object createGenericActionMenuAdapter(GenericAction action);

  public abstract Object createGenericActionAdapter(GenericAction action);

  protected abstract JPopupMenu createPopup(String key, List actions);

  public abstract Object createGroup(String name);

  public abstract Object createMenuAction(RegexPanel panel, Icon i, JPopupMenu popup);

  public abstract Object createToggleAction(GenericToggleAction action);

  public abstract Object createToggleMenuAction(GenericToggleAction action);

  public abstract void addToGroup(Object group, Object something);

  public abstract JComponent getComponent(String name, Object group, boolean horizontal);

}