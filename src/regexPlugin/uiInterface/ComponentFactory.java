package regexPlugin.uiInterface;

public class ComponentFactory {
  private ComponentFactory() {
  }

  public enum Type {SWING, IDEA}

  public static ComponentManager getInstance(Type type) {
    switch (type) {
      case SWING:
        return Swing.createOrGetInstance();
      default:
      case IDEA:
        return Idea.createOrGetInstance();
    }
  }
}
