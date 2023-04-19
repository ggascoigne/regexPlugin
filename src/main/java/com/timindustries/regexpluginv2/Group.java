package com.timindustries.regexpluginv2;

import java.util.regex.Matcher;

class Group {
  String text;

  int startIdx;

  int endIdx;

  public Group(Matcher m, int groupIdx) {
    text = m.group(groupIdx);
    startIdx = m.start(groupIdx);
    endIdx = m.end(groupIdx);
  }

  public String getText() {
    return text;
  }

  public int getStartIdx() {
    return startIdx;
  }

  public int getEndIdx() {
    return endIdx;
  }
}
