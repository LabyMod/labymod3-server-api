package net.labymod.serverapi.velocity.payload;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.connection.PluginMessageEvent.ForwardResult;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import java.util.UUID;
import net.labymod.serverapi.api.payload.PayloadChannelRegistrar;
import net.labymod.serverapi.api.payload.PayloadChannelType;
import net.labymod.serverapi.common.payload.DefaultPayloadCommunicator;
import net.labymod.serverapi.velocity.event.VelocitySendPayloadEvent;
import net.labymod.serverapi.velocity.payload.event.VelocityReceivePlayerPayloadEvent;
import net.labymod.serverapi.velocity.player.VelocityLabyModPlayerService;

public class VelocityPayloadCommunicator extends DefaultPayloadCommunicator {

  private final ProxyServer proxyServer;
  private final PayloadChannelRegistrar<ChannelIdentifier> payloadChannelRegistrar;

  public VelocityPayloadCommunicator(
      ProxyServer proxyServer, PayloadChannelRegistrar<ChannelIdentifier> payloadChannelRegistrar) {
    super(VelocityLabyModPlayerService.getInstance());
    this.proxyServer = proxyServer;
    this.payloadChannelRegistrar = payloadChannelRegistrar;
  }

  /** {@inheritDoc} */
  @Override
  public void send(UUID uniqueId, String identifier, byte[] payload) {
    this.proxyServer
        .getPlayer(uniqueId)
        .ifPresent(
            player -> {
              PayloadChannelType channelType =
                  identifier.contains(":") ? PayloadChannelType.MODERN : PayloadChannelType.LEGACY;

              this.payloadChannelRegistrar.getChannelIdentifiers().get(channelType).stream()
                  .filter(channelIdentifier -> channelIdentifier.getId().equals(identifier))
                  .forEach(
                      channelIdentifier -> {
                        VelocitySendPayloadEvent velocitySendPayloadEvent =
                            new VelocitySendPayloadEvent(player, channelIdentifier, payload);
                        this.proxyServer.getEventManager().fire(velocitySendPayloadEvent);

                        if (velocitySendPayloadEvent.getResult() == ForwardResult.forward()) {
                          player.sendPluginMessage(channelIdentifier, payload);
                        }
                      });
            });
  }
  /** {@inheritDoc} */
  @Override
  public void receive(UUID uniqueId, String identifier, byte[] payload) {
    this.proxyServer
        .getEventManager()
        .fire(new VelocityReceivePlayerPayloadEvent(uniqueId, identifier, payload));
  }

  @Subscribe
  public void receivePluginMessage(PluginMessageEvent event) {
    if (event.getSource() instanceof Player) {
      receive(
          ((Player) event.getSource()).getUniqueId(),
          event.getIdentifier().getId(),
          event.getData());
    }
  }
}
