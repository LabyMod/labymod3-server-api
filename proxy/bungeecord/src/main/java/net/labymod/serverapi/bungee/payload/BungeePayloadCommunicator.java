package net.labymod.serverapi.bungee.payload;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.UUID;
import net.labymod.serverapi.api.payload.PayloadBuffer.Factory;
import net.labymod.serverapi.api.payload.PayloadChannelRegistrar;
import net.labymod.serverapi.api.payload.PayloadChannelType;
import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.bungee.BungeeLabyModPlugin;
import net.labymod.serverapi.bungee.event.BungeeReceivePayloadEvent;
import net.labymod.serverapi.bungee.event.BungeeSendPayloadEvent;
import net.labymod.serverapi.common.payload.DefaultPayloadCommunicator;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.event.EventHandler;

@Singleton
public class BungeePayloadCommunicator extends DefaultPayloadCommunicator implements Listener {

  private final PayloadChannelRegistrar<String> payloadChannelRegistrar;
  private final BungeeLabyModPlugin plugin;
  private final PluginManager pluginManager;

  @Inject
  private BungeePayloadCommunicator(
      LabyModPlayerService<ProxiedPlayer> labyModPlayerService,
      Factory payloadBufferFactory,
      PayloadChannelRegistrar<String> payloadChannelRegistrar,
      BungeeLabyModPlugin plugin) {
    super(labyModPlayerService, payloadBufferFactory);
    this.payloadChannelRegistrar = payloadChannelRegistrar;
    this.plugin = plugin;
    this.pluginManager = this.plugin.getProxy().getPluginManager();
  }

  /** {@inheritDoc} */
  @Override
  public void send(UUID uniqueId, String identifier, byte[] payload) {
    ProxiedPlayer proxiedPlayer = this.plugin.getProxy().getPlayer(uniqueId);

    if (proxiedPlayer == null) {
      return;
    }

    PayloadChannelType channelType =
        identifier.contains(":") ? PayloadChannelType.MODERN : PayloadChannelType.LEGACY;

    this.payloadChannelRegistrar.getChannelIdentifiers().get(channelType).stream()
        .filter(channelIdentifier -> channelIdentifier.equals(identifier))
        .forEach(
            channelIdentifier -> {
              BungeeSendPayloadEvent bungeeSendPayloadEvent =
                  new BungeeSendPayloadEvent(proxiedPlayer, channelIdentifier, payload);
              this.pluginManager.callEvent(bungeeSendPayloadEvent);

              if (bungeeSendPayloadEvent.isCancelled()) {
                return;
              }

              proxiedPlayer.sendData(channelIdentifier, payload);
            });
  }

  /** {@inheritDoc} */
  @Override
  public void receive(UUID uniqueId, String identifier, byte[] payload) {
    this.pluginManager.callEvent(new BungeeReceivePayloadEvent(uniqueId, identifier, payload));
  }

  @EventHandler
  public void pluginMessage(PluginMessageEvent event) {
    if (event.getSender() instanceof ProxiedPlayer) {
      this.receive(
          ((ProxiedPlayer) event.getSender()).getUniqueId(), event.getTag(), event.getData());
    }
  }
}
