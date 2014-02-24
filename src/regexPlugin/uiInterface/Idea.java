package regexPlugin.uiInterface;

import com.intellij.ide.highlighter.JavaHighlightingColors;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.colors.EditorFontType;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.colors.impl.EditorColorsSchemeImpl;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.ui.components.JBScrollPane;
import regexPlugin.RegexPanel;
import regexPlugin.actions.GenericAction;
import regexPlugin.actions.GenericToggleAction;
import regexPlugin.actions.IdeaAction;
import regexPlugin.actions.IdeaMenuAction;
import regexPlugin.regexEditor.TokenType;
import regexPlugin.ui.Resources;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Idea extends ComponentFactory {

  public Idea() {
    assert (instance == null);
    instance = this;
  }

  public Object createGroup(String name) {
    return new DefaultActionGroup(name, false);
  }

  public Object createMenuAction(RegexPanel panel, Icon i, JPopupMenu popup) {
    return new MenuAction(popup, i);
  }

  public void addToGroup(Object group, Object something) {
    DefaultActionGroup ag = (DefaultActionGroup) group;
    ag.add((AnAction) something);
  }

  public JComponent getComponent(String name, Object group, boolean horizontal) {
    ActionToolbar actionToolbar = ActionManager.getInstance()
      .createActionToolbar(name, (ActionGroup) group, horizontal);
    return actionToolbar.getComponent();
  }

  public Object createToggleAction(GenericToggleAction action) {
    return new ToggleActionAdapter(action);
  }

  public Object createToggleMenuAction(GenericToggleAction action) {
    final ToggleActionAdapter taa = new ToggleActionAdapter(action);
    taa.setSelected(null, action.isSelected());
    return taa;
  }

  protected JPopupMenu createPopup(String name, List actions) {
    final DefaultActionGroup group = new DefaultActionGroup(name, true);
    for (Iterator iterator = actions.iterator(); iterator.hasNext(); ) {
      AnAction action = (AnAction) iterator.next();
      group.add(action);
    }

    final ActionPopupMenu popup = ActionManager.getInstance()
      .createActionPopupMenu("RegexPlugin", group);
    return popup.getComponent();
  }

  protected Object createSubMenu(String key, List actions) {
    DefaultActionGroup menu = new DefaultActionGroup(Resources.getLabel(key),
      true);
    addToMenu(menu, actions);
    return menu;
  }

  public Object createGenericActionMenuAdapter(GenericAction action) {
    return new IdeaMenuAction(action);
  }

  public Object createGenericActionAdapter(GenericAction action) {
    return new IdeaAction(action);
  }

  private void addToMenu(DefaultActionGroup menu, List actions) {
    for (Iterator iterator = actions.iterator(); iterator.hasNext(); ) {
      GenericAction action = (GenericAction) iterator.next();
      menu.add((AnAction) createGenericActionMenuAdapter(action));
    }
  }

  class ToggleActionAdapter extends ToggleAction {
    GenericToggleAction action;

    public ToggleActionAdapter(GenericToggleAction action) {
      super(action.getName(), action.getName(), action.getIcon());
      this.action = action;
    }

    public boolean isSelected(AnActionEvent event) {
      return action.isSelected();
    }

    public void setSelected(AnActionEvent event, boolean b) {
      action.setSelected(b);
    }

    public boolean displayTextInToolbar() {
      return action.showDescription();
    }
  }

  class MenuAction extends AnAction {
    private JPopupMenu menu;

    public MenuAction(JPopupMenu menu, Icon i) {
      super(Resources.getLabel("menu"), Resources.getLabel("menu"), i);
      this.menu = menu;
    }

    public void actionPerformed(AnActionEvent event) {
      menu.show(event.getInputEvent().getComponent(), 0, 0);
    }
  }

  public JScrollPane getScrollPane(java.awt.Component component) {
    return new JBScrollPane(component);
  }

  @Override
  public Color getDefaultBackgroundColor() {
    EditorColorsScheme globalScheme = EditorColorsManager.getInstance().getGlobalScheme();
    return globalScheme.getDefaultBackground();
  }

  @Override
  public Font getDefaultFont() {
    EditorColorsScheme globalScheme = EditorColorsManager.getInstance().getGlobalScheme();
    return globalScheme.getFont(EditorFontType.CONSOLE_PLAIN);
  }

  @Override
  public StyleTuple getStyleFor(TokenType type) {
    switch (type) {
      case WHITESPACE:
        return getTupleFor(JavaHighlightingColors.STRING);
      case WORD:
        return getTupleFor(JavaHighlightingColors.STRING);
      case NUMBER:
        return getTupleFor(JavaHighlightingColors.NUMBER);
      case PUNCTUATION:
        return getTupleFor(JavaHighlightingColors.JAVA_SEMICOLON);
      case COMMENT:
        return getTupleFor(JavaHighlightingColors.LINE_COMMENT);
      case CHARACTER:
        return getTupleFor(JavaHighlightingColors.VALID_STRING_ESCAPE);
      case CLASS:
        return getTupleFor(DefaultLanguageHighlighterColors.CONSTANT);
      case BOUNDARY:
        return getTupleFor(JavaHighlightingColors.KEYWORD);
      case QUANTIFIER:
        return getTupleFor(JavaHighlightingColors.KEYWORD);
      default:
      case UNRECOGNIZED:
        return getTupleFor(JavaHighlightingColors.INVALID_STRING_ESCAPE);
    }
  }

  private StyleTuple getTupleFor(String key) {
    return getTupleFor(TextAttributesKey.find(key));
  }

  private StyleTuple getTupleFor(TextAttributesKey key) {
    EditorColorsScheme globalScheme = EditorColorsManager.getInstance().getGlobalScheme();
    TextAttributes attributes = globalScheme.getAttributes(key);
    Color color = attributes.getForegroundColor();
    if (color == null) {
      color = globalScheme.getDefaultForeground();
      System.out.println("falling back to default color for " + key.getExternalName());
    }
    int type = attributes.getFontType();
    return new StyleTuple(color, type);
  }

  @Override
  public Color getMatchColor1() {
    EditorColorsScheme globalScheme = EditorColorsManager.getInstance().getGlobalScheme();
    TextAttributes attributes = globalScheme.getAttributes(JavaHighlightingColors.STRING);
    return attributes.getForegroundColor();
  }

  @Override
  public Color getMatchColor2() {
    EditorColorsScheme globalScheme = EditorColorsManager.getInstance().getGlobalScheme();
    TextAttributes attributes = globalScheme.getAttributes(JavaHighlightingColors.NUMBER);
    return attributes.getForegroundColor();
  }
}
