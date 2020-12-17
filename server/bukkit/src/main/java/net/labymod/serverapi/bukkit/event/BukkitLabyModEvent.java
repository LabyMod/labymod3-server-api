package net.labymod.serverapi.bukkit.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/** Represents the base event for bukkit. */
public class BukkitLabyModEvent extends Event {

  private static final HandlerList HANDLER_LIST = new HandlerList();

  public static HandlerList getHandlerList() {
    return HANDLER_LIST;
  }

  /** {@inheritDoc} */
  @Override
  public HandlerList getHandlers() {
    return HANDLER_LIST;
  }
}
