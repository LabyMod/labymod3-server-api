package net.labymod.serverapi.bukkit.event;

import com.google.gson.JsonElement;
import org.bukkit.entity.Player;

public class BukkitMessageReceiveEvent extends BukkitLabyModEvent {

  private final Player player;
  private final String messageKey;
  private final JsonElement messageContent;

  public BukkitMessageReceiveEvent(Player player, String messageKey, JsonElement messageContent) {
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
