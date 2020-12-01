package net.labymod.serverapi.common.serverinteraction.actionmenu;

import net.labymod.serverapi.api.serverinteraction.actionmenu.ActionType;
import net.labymod.serverapi.api.serverinteraction.actionmenu.MenuEntry;

public class DefaultMenuEntryFactory implements MenuEntry.Factory {

  /** {@inheritDoc} */
  @Override
  public MenuEntry create(String displayName, String value, ActionType actionType) {
    return new DefaultMenuEntry(displayName, value, actionType);
  }
}
