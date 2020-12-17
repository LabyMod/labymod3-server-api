package net.labymod.serverapi.bukkit.event;

import com.google.gson.JsonElement;
import org.bukkit.entity.Player;

/** Is fired when a custom payload message is received from the client. */
public class BukkitMessageReceiveEvent extends BukkitLabyModEvent {

  private final Player player;
  private final String messageKey;
  private final JsonElement messageContent;

  public BukkitMessageReceiveEvent(Player player, String messageKey, JsonElement messageContent) {
    this.player = player;
    this.messageKey = messageKey;
    this.messageContent = messageContent;
  }

  /**
   * Retrieves the player that received the payload message.
   *
   * @return The player that received the payload message.
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Retrieves the key assigned to the message content.
   *
   * @return The key assigned to the message content.
   */
  public String getMessageKey() {
    return messageKey;
  }

  /**
   * Retrieves the message content as a JSON tree.
   *
   * @return The message content as a JSON tree.
   */
  public JsonElement getMessageContent() {
    return messageContent;
  }
}
