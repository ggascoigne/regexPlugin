package com.timindustries.regexpluginv2;

import java.net.URL;
import java.util.HashMap;

import javax.swing.*;

public class IconCache {

  HashMap<String,Icon> icons = new HashMap<String,Icon>();

  public Icon getIcon(String resource) {
    Icon res = icons.get(resource);
    if (res != null) {
      return res;
    } else {
      return insertIcon(resource);
    }
  }

  private Icon insertIcon(String resource) {
    URL u = getClass().getResource("/icons/" + resource);
    if (u == null) {
      return null;
    } else {
      ImageIcon res = null;
      try {
        res = new ImageIcon(u);
      } catch (Exception e) {
        e.printStackTrace();
      }
      icons.put(resource, res);
      return res;
    }
  }

}
