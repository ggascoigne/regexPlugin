package com.timindustries.regexplugin;

import java.util.ArrayList;
import java.util.regex.Matcher;

class Match {

  ArrayList<Group> groups = new ArrayList<Group>();

  public Match(Matcher matcher) {
    for (int i = 0; i <= matcher.groupCount(); i++) {
      groups.add(new Group(matcher, i));
    }
  }

  public String getText() {
    return groups.get(0).getText();
  }

  public int getStartIdx() {
    return groups.get(0).getStartIdx();
  }

  public int getEndIdx() {
    return groups.get(0).getEndIdx();
  }

  public ArrayList<Group> getGroups() {
    return groups;
  }

  public String toString() {
    return "Match (\"" + getText() + "\", " + getStartIdx() + ", " + getEndIdx() +
           ")";
  }

  public boolean isRealMatch() {
    return getStartIdx() != getEndIdx();
  }
}
