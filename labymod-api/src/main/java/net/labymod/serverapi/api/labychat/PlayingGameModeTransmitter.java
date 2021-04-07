package net.labymod.serverapi.api.labychat;

import java.util.UUID;

/** The LabyMod chat can display the name of the current game mode on the server. */
public interface PlayingGameModeTransmitter {

  /**
   * Transmits the current game mode to a player with the specified unique identifier.
   *
   * @param uniqueId The unique identifier of a player.
   * @param visible {@code true} if the current game mode should be displayed in LabyChat, otherwise
   *     {@code false}.
   * @param gameModeName The name of the game mode.
   */
  void transmit(UUID uniqueId, boolean visible, String gameModeName);

  /**
   * Transmits the current game mode to a player with the specified unique identifier.
   *
   * @param uniqueId The unique identifier of a player.
   * @param gameModeName The name of the game mode.
   */
  default void transmit(UUID uniqueId, String gameModeName) {
    this.transmit(uniqueId, true, gameModeName);
  }

  /**
   * Transmits the current game mode to all online laby users.
   *
   * @param visible {@code true} if the current game mode should be displayed in LabyChat, otherwise
   *     {@code false}.
   * @param gameModeName The name of the game mode.
   */
  void broadcastTransmit(boolean visible, String gameModeName);

  /**
   * Transmits the current game mode to all online laby users.
   *
   * @param gameModeName The name of the game mode.
   */
  default void broadcastTransmit(String gameModeName) {
    this.broadcastTransmit(true, gameModeName);
  }
}
