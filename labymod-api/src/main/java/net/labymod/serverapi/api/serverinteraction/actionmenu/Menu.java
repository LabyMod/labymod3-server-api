package net.labymod.serverapi.api.serverinteraction.actionmenu;

import java.util.UUID;

/**
 * Represents the action menu of a player.
 *
 * <p><b>Overview:</b>
 *
 * <p>In version <b>3.3.1</b>, we introduced a middle click for players. It features custom actions
 * are executed for the player that has been clicked on. A server can add custom buttons to that
 * list of actions.
 *
 * @deprecated {@link MenuTransmitter}
 */
@Deprecated
public interface Menu {

  /**
   * Adds a menu entry to a list, which should then be displayed in the menu.
   *
   * @param entry The menu entry which should be displayed.
   * @return This object for a fluent chaining.
   * @deprecated {@link MenuTransmitter#addEntry(MenuEntry)}
   */
  @Deprecated
  Menu addEntry(MenuEntry entry);

  /**
   * Adds to a list an array of menu entries, which should then be displayed in the menu.
   *
   * @param entries An array menu entries which should be displayed.
   * @return This object for a fluent chaining.
   * @deprecated {@link MenuTransmitter#addEntries(MenuEntry...)}
   */
  @Deprecated
  Menu addEntries(MenuEntry... entries);

  /**
   * Transmits an action menu to the client.
   *
   * @param uniqueId The unique identifier of the receiver.
   * @deprecated {@link MenuTransmitter#transmit(UUID)}
   */
  @Deprecated
  void transmit(UUID uniqueId);

  /**
   * Transmits an action menu to all online laby users.
   *
   * @deprecated {@link MenuTransmitter#broadcastTransmit()}
   */
  @Deprecated
  void broadcastTransmit();
}
