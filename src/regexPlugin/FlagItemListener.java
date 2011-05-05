package regexPlugin;

class FlagItemListener
{
}/*implements ItemListener {
    
  static public boolean m_disabled = false;
  private MatchAction matchAction;

  private int flag;

  FlagItemListener(final MatchAction matchAction, final int flag) {
    this.matchAction = matchAction;
    this.flag = flag;
  }

  public void itemStateChanged(final ItemEvent e) {
    if (!m_disabled) {
      if (e.getStateChange() == ItemEvent.SELECTED) {
        matchAction.addFlag(flag);
      } else {
        matchAction.removeFlag(flag);
      }
      matchAction.actionPerformed(null);
    }
  }

  static public void setDisabled(final boolean disabled) {
    m_disabled = disabled;
  }
}
  */
