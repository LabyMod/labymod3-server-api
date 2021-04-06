package net.labymod.serverapi.bungee.payload;

import net.labymod.serverapi.api.LabyService;
import net.labymod.serverapi.api.payload.PayloadChannelRegistrar;
import net.labymod.serverapi.api.payload.PayloadChannelType;
import net.labymod.serverapi.bungee.BungeeLabyModPlugin;
import net.labymod.serverapi.bungee.event.BungeeReceivePayloadEvent;
import net.labymod.serverapi.bungee.event.BungeeSendPayloadEvent;
import net.labymod.serverapi.common.payload.DefaultPayloadCommunicator;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

public class BungeePayloadCommunicator extends DefaultPayloadCommunicator implements Listener {

  private final BungeeLabyModPlugin plugin;
  private final PluginManager pluginManager;
  private PayloadChannelRegistrar<String> payloadChannelRegistrar;

  public BungeePayloadCommunicator(LabyService service, BungeeLabyModPlugin plugin) {
    super(service);
    this.plugin = plugin;
    this.pluginManager = this.plugin.getProxy().getPluginManager();
  }

  public void setPayloadChannelRegistrar(
      final PayloadChannelRegistrar<String> payloadChannelRegistrar) {
    this.payloadChannelRegistrar = payloadChannelRegistrar;
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
