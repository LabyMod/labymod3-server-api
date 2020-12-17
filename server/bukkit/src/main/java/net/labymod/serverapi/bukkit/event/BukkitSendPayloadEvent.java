package net.labymod.serverapi.bukkit.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

/** Is fired when a custom payload is sent. */
public class BukkitSendPayloadEvent extends BukkitLabyModEvent implements Cancellable {

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

  /**
   * Retrieves the player to which the payload is sent.
   *
   * @return The player to which the payload is sent.
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Retrieves the channel identifier to which the payload is sent.
   *
   * @return The channel identifier to which the payload is sent.
   */
  public String getChannelIdentifier() {
    return channelIdentifier;
  }

  /**
   * Changes the channel identifier.
   *
   * @param channelIdentifier The new channel identifier.
   */
  public void setChannelIdentifier(String channelIdentifier) {
    this.channelIdentifier = channelIdentifier;
  }

  /**
   * Retrieves the sent payload message.
   *
   * @return The sent payload message.
   */
  public byte[] getPayload() {
    return payload;
  }

  /**
   * Changes the sent payload message.
   *
   * @param payload The new payload message.
   */
  public void setPayload(byte[] payload) {
    this.payload = payload;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isCancelled() {
    return this.cancelled;
  }

  /** {@inheritDoc} */
  @Override
  public void setCancelled(boolean cancel) {
    this.cancelled = cancel;
  }
}
