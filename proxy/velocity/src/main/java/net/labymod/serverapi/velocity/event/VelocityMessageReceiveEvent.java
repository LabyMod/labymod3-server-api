package net.labymod.serverapi.velocity.event;

import com.google.gson.JsonElement;
import com.velocitypowered.api.proxy.Player;

/** Is fired when a payload message is received. */
public class VelocityMessageReceiveEvent {

  private final Player player;
  private final String messageKey;
  private final JsonElement messageContent;

  public VelocityMessageReceiveEvent(Player player, String messageKey, JsonElement messageContent) {
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
