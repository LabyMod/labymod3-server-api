package net.labymod.serverapi.api.labychat;

import java.util.UUID;

/** The LabyMod chat can display the name of the current game mode on the server. */
public interface PlayingGameMode {

  /**
   * Sends the current game mode to a player with the specified unique identifier.
   *
   * @param uniqueId The unique identifier of a player.
   * @param visible {@code true} if the current game mode should be displayed in LabyChat, otherwise
   *     {@code false}.
   * @param gameModeName The name of the game mode.
   */
  void sendCurrentPlayingGameMode(UUID uniqueId, boolean visible, String gameModeName);

  /**
   * Sends the current game mode to a player with the specified unique identifier.
   *
   * <p><b>Note:</b> The functions shows the current game mode in LabyChat.
   *
   * @param uniqueId The unique identifier of a player.
   * @param gameModeName The name of the game mode.
   */
  default void sendCurrentPlayingGameMode(UUID uniqueId, String gameModeName) {
    this.sendCurrentPlayingGameMode(uniqueId, true, gameModeName);
  }
}
