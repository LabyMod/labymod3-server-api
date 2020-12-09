package net.labymod.serverapi.bungee.event;

import com.google.gson.JsonElement;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

/** Is fired when a payload message is received. */
public class BungeeMessageReceiveEvent extends Event {

  private final ProxiedPlayer player;
  private final String messageKey;
  private final JsonElement messageContent;

  public BungeeMessageReceiveEvent(
      ProxiedPlayer player, String messageKey, JsonElement messageContent) {
    this.player = player;
    this.messageKey = messageKey;
    this.messageContent = messageContent;
  }

  /**
   * Retrieves the player that received the payload message.
   *
   * @return The player that received the payload message.
   */
  public ProxiedPlayer getPlayer() {
    return player;
  }

  /**
   * Retrieves the key assigned to the message content.
   *
   * @return THe key assigned to the message content.
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
