package net.labymod.serverapi.velocity.event;

import com.velocitypowered.api.event.ResultedEvent;
import com.velocitypowered.api.event.connection.PluginMessageEvent.ForwardResult;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;

/** Is fired when a custom payload is sent. */
public class VelocitySendPayloadEvent implements ResultedEvent<ForwardResult> {

  private final Player player;
  private final ChannelIdentifier channelIdentifier;
  private final byte[] payload;
  private ForwardResult result;

  public VelocitySendPayloadEvent(
      Player player, ChannelIdentifier channelIdentifier, byte[] payload) {
    this(player, channelIdentifier, payload, ForwardResult.forward());
  }

  public VelocitySendPayloadEvent(
      Player player, ChannelIdentifier channelIdentifier, byte[] payload, ForwardResult result) {
    this.player = player;
    this.channelIdentifier = channelIdentifier;
    this.payload = payload;
    this.result = result;
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
   * @return The channel identifier to
   */
  public ChannelIdentifier getChannelIdentifier() {
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
  public ForwardResult getResult() {
    return this.result;
  }

  /** {@inheritDoc} */
  @Override
  public void setResult(ForwardResult result) {
    this.result = result;
  }
}
