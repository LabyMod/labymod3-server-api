package net.labymod.serverapi.bukkit.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BukkitSendPayloadEvent extends Event implements Cancellable {

  private static final HandlerList HANDLER_LIST = new HandlerList();

  private final Player player;
  private String channelIdentifier;
  private byte[] payload;
  private boolean cancelled;

  public BukkitSendPayloadEvent(Player player, String channelIdentifier, byte[] payload) {
    this(player, channelIdentifier, payload, false);
  }

  public BukkitSendPayloadEvent(
      Player player, String channelIdentifier, byte[] payload, boolean cancelled) {
    this.player = player;
    this.channelIdentifier = channelIdentifier;
    this.payload = payload;
    this.cancelled = cancelled;
  }

  public static HandlerList getHandlerList() {
    return HANDLER_LIST;
  }

  public Player getPlayer() {
    return player;
  }

  public String getChannelIdentifier() {
    return channelIdentifier;
  }

  public void setChannelIdentifier(String channelIdentifier) {
    this.channelIdentifier = channelIdentifier;
  }

  public byte[] getPayload() {
    return payload;
  }

  public void setPayload(byte[] payload) {
    this.payload = payload;
  }

  @Override
  public boolean isCancelled() {
    return this.cancelled;
  }

  @Override
  public void setCancelled(boolean cancel) {
    this.cancelled = cancel;
  }

  @Override
  public HandlerList getHandlers() {
    return HANDLER_LIST;
  }
}
