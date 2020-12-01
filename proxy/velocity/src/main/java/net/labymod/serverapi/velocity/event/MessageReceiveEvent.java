package net.labymod.serverapi.velocity.event;

import com.google.gson.JsonElement;
import com.velocitypowered.api.proxy.Player;

public class MessageReceiveEvent {

  private final Player player;
  private final String messageKey;
  private final JsonElement messageContent;

  public MessageReceiveEvent(Player player, String messageKey,
      JsonElement messageContent) {
    this.player = player;
    this.messageKey = messageKey;
    this.messageContent = messageContent;
  }

  public Player getPlayer() {
    return player;
  }

  public String getMessageKey() {
    return messageKey;
  }

  public JsonElement getMessageContent() {
    return messageContent;
  }
}
