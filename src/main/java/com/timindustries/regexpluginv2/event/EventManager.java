package com.timindustries.regexpluginv2.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.timindustries.regexpluginv2.Utils;

public class EventManager {
  protected final Map<Notifiable, List<ObjectListener>> m_listeners =
      new HashMap<Notifiable, List<ObjectListener>>();

  protected final Map<Class, List<ObjectListener>> m_listenersByType =
      new HashMap<Class, List<ObjectListener>>();

  protected static final EventManager m_instance = new EventManager();

  protected EventManager() {
  }

  public static EventManager getInstance() {
    return m_instance;
  }

  private static final Notifiable m_globalTarget = new Notifiable() {
  };

  public static Notifiable getGlobalTarget() {
    return m_globalTarget;
  }

  /**
   * Add the given listener to the list of listeners that will be notified of
   * any events related to the given object.
   *
   * @param object   the object being listened for
   * @param listener the listener that will be notified
   */
  public void addObjectListener(final Notifiable object,
      final ObjectListener listener) {
    synchronized (m_listeners) {
      List<ObjectListener> listeners = m_listeners.get(object);

      if (listeners == null) {
        listeners = new ArrayList<ObjectListener>();
        m_listeners.put(object, listeners);
      }

      listeners.add(listener);
    }
  }

  /**
   * Add the given listener to the list of listeners that will be notified of
   * any events related to the given type of space object.
   *
   * @param type     the type of space object being listened for
   * @param listener the listener that will be notified
   */
  public void addObjectListener(final Class type, final ObjectListener listener) {
    synchronized (m_listenersByType) {
      List<ObjectListener> listeners = m_listenersByType.get(type);

      if (listeners == null) {
        listeners = new ArrayList<ObjectListener>();
        m_listenersByType.put(type, listeners);
      }

      listeners.add(listener);
    }
  }

  /**
   * Remove the given listener from the list of listeners that will be
   * notified of any events related to the given object.
   *
   * @param object   the object no longer being listened for
   * @param listener the listener that will be removed
   */
  public void removeObjectListener(final Notifiable object,
      final ObjectListener listener) {
    synchronized (m_listeners) {
      final List<ObjectListener> listeners = m_listeners.get(object);

      if (listeners != null) {
        remove(listeners, listener);

        if (listeners.isEmpty()) {
          m_listeners.remove(object);
        }
      }
    }
  }

  /**
   * Remove the given listener from the list of listeners that will be
   * notified of any events related to the given type of space object.
   *
   * @param type     the type of space object no longer being listened for
   * @param listener the listener that will be removed
   */
  public void removeObjectListener(final Class type, final ObjectListener listener) {
    synchronized (m_listenersByType) {
      final List<ObjectListener> listeners = m_listenersByType.get(type);

      if (listeners != null) {
        remove(listeners, listener);

        if (listeners.isEmpty()) {
          m_listenersByType.remove(type);
        }
      }
    }
  }

  public void fireEvent(final Notifiable source, final String message,
      final Object object) {
    final ObjectEvent event = new ObjectEvent(source, message, object);
    for (ObjectListener ref : getListeners(source)) {
      try {
        ref.event(event);
      } catch (Exception e) {
        Utils.handleException("error.fireEvent", e);
      }
    }
  }

  /**
   * Return a list of the listeners that should be notified of events related
   * to the given object.
   *
   * @param object the object related to the event
   * @return a list of the listeners that should be notified
   */
  protected List<ObjectListener> getListeners(final Notifiable object) {
    final List<ObjectListener> allListeners;
    List<ObjectListener> listeners;
    allListeners = new ArrayList<ObjectListener>();

    synchronized (m_listeners) {
      listeners = m_listeners.get(object);

      if (listeners != null) {
        allListeners.addAll(listeners);
      }
    }
    synchronized (m_listenersByType) {
      Class type = object.getClass();

      while (type != null) {
        listeners = m_listenersByType.get(type);
        if (listeners != null) {
          allListeners.addAll(listeners);
        }
        type = type.getSuperclass();
      }
    }
    return allListeners;
  }

  protected void remove(final Collection col, final Object ob) {
    final Iterator it = col.iterator();
    while (it.hasNext()) {
      if (it.next() == ob) {
        it.remove();
      }
    }
  }
}
