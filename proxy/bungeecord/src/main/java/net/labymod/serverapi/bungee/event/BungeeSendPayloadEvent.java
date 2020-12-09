package net.labymod.serverapi.bungee.event;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Event;

/** Is fired when a custom payload is sent. */
public class BungeeSendPayloadEvent extends Event implements Cancellable {

  private final ProxiedPlayer player;
  private final String channelIdentifier;
  private final byte[] payload;
  private boolean cancelled;

  public BungeeSendPayloadEvent(ProxiedPlayer player, String channelIdentifier, byte[] payload) {
    this(player, channelIdentifier, payload, false);
  }

  public BungeeSendPayloadEvent(
      ProxiedPlayer player, String channelIdentifier, byte[] payload, boolean cancelled) {
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
  public ProxiedPlayer getPlayer() {
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
   * Retrieves the sent payload message.
   *
   * @return The sent payload message.
   */
  public byte[] getPayload() {
    return payload;
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
