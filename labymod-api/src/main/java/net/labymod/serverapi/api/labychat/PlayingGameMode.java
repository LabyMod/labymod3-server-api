package net.labymod.serverapi.api.labychat;

import java.util.UUID;

/**
 * The LabyMod chat can display the name of the current game mode on the server.
 *
 * @deprecated Use {@link PlayingGameModeTransmitter}
 */
@Deprecated
public interface PlayingGameMode {

  /**
   * Sends the current game mode to a player with the specified unique identifier.
   *
   * @param uniqueId The unique identifier of a player.
   * @param visible {@code true} if the current game mode should be displayed in LabyChat, otherwise
   *     {@code false}.
   * @param gameModeName The name of the game mode.
   * @deprecated Use {@link PlayingGameModeTransmitter#transmit(UUID, boolean, String)}
   */
  @Deprecated
  void sendCurrentPlayingGameMode(UUID uniqueId, boolean visible, String gameModeName);

  /**
   * Sends the current game mode to a player with the specified unique identifier.
   *
   * <p><b>Note:</b> The functions shows the current game mode in LabyChat.
   *
   * @param uniqueId The unique identifier of a player.
   * @param gameModeName The name of the game mode.
   * @deprecated Use {@link PlayingGameModeTransmitter#transmit(UUID, String)}
   */
  @Deprecated
  default void sendCurrentPlayingGameMode(UUID uniqueId, String gameModeName) {
    this.sendCurrentPlayingGameMode(uniqueId, true, gameModeName);
  }

  /** @deprecated Use {@link PlayingGameModeTransmitter#broadcastTransmit(boolean, String)} */
  @Deprecated
  void broadcastCurrentlyPlayingGameMode(boolean visible, String gameModeName);

  /** @deprecated Use {@link PlayingGameModeTransmitter#broadcastTransmit(String)} */
  @Deprecated
  default void broadcastCurrentlyPlayingGameMode(String gameModeName) {
    this.broadcastCurrentlyPlayingGameMode(true, gameModeName);
  }
}
