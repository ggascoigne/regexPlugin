package com.wyrdrune.regexplugin.uiInterface;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPopupMenu;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.actionSystem.ToggleAction;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.colors.EditorFontType;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.ui.components.JBScrollPane;
import com.wyrdrune.regexplugin.RegexPanel;
import com.wyrdrune.regexplugin.actions.GenericAction;
import com.wyrdrune.regexplugin.actions.GenericToggleAction;
import com.wyrdrune.regexplugin.actions.IdeaAction;
import com.wyrdrune.regexplugin.actions.IdeaMenuAction;
import com.wyrdrune.regexplugin.regexEditor.TokenType;
import com.wyrdrune.regexplugin.ui.Resources;

public class Idea extends ComponentManager {

  static ComponentManager createOrGetInstance() {
    if (instance == null) {
      instance = new Idea();
    }
    return instance;
  }

  private Idea() {
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

  public JScrollPane getScrollPane(Component component) {
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
      case WORD:
        return getTupleFor(DefaultLanguageHighlighterColors.STRING);
      case NUMBER:
        return getTupleFor(DefaultLanguageHighlighterColors.NUMBER);
      case PUNCTUATION:
        return getTupleFor(DefaultLanguageHighlighterColors.SEMICOLON);
      case COMMENT:
        return getTupleFor(DefaultLanguageHighlighterColors.LINE_COMMENT);
      case CHARACTER:
        return getTupleFor(DefaultLanguageHighlighterColors.VALID_STRING_ESCAPE);
      case CLASS:
        return getTupleFor(DefaultLanguageHighlighterColors.CONSTANT);
      case BOUNDARY:
      case QUANTIFIER:
        return getTupleFor(DefaultLanguageHighlighterColors.KEYWORD);
      default:
      case UNRECOGNIZED:
        return getTupleFor(DefaultLanguageHighlighterColors.INVALID_STRING_ESCAPE);
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
    TextAttributes attributes = globalScheme.getAttributes(DefaultLanguageHighlighterColors.STRING);
    return attributes.getForegroundColor();
  }

  @Override
  public Color getMatchColor2() {
    EditorColorsScheme globalScheme = EditorColorsManager.getInstance().getGlobalScheme();
    TextAttributes attributes = globalScheme.getAttributes(DefaultLanguageHighlighterColors.NUMBER);
    return attributes.getForegroundColor();
  }
}
