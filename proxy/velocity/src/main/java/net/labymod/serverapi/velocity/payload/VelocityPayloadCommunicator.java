package net.labymod.serverapi.velocity.payload;

import com.google.gson.JsonElement;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import java.util.UUID;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.velocity.VelocityLabyModPlugin;
import net.labymod.serverapi.velocity.payload.event.ReceivePlayerPayloadEvent;

public class VelocityPayloadCommunicator implements PayloadCommunicator {

  private final ProxyServer proxyServer;

  public VelocityPayloadCommunicator(VelocityLabyModPlugin plugin, ProxyServer proxyServer) {
    this.proxyServer = proxyServer;
    this.proxyServer.getEventManager().register(plugin, this);
  }

  /** {@inheritDoc} */
  @Override
  public void send(UUID uniqueId, String identifier, byte[] payload) {
    this.proxyServer
        .getPlayer(uniqueId)
        .ifPresent(
            player -> {
              player.sendPluginMessage(null, payload);
            });
  }

  @Override
  public void sendLabyModMessage(UUID uniqueId, String messageKey, JsonElement messageContent) {
    // TODO: 01.12.2020 Implementation
  }

  /** {@inheritDoc} */
  @Override
  public void receive(UUID uniqueId, String identifier, byte[] payload) {
    this.proxyServer
        .getEventManager()
        .fire(new ReceivePlayerPayloadEvent(uniqueId, identifier, payload));
  }

  @Subscribe
  public void receivePluginMessage(PluginMessageEvent event) {
    if (event.getSource() instanceof Player) {
      receive(
          ((Player) event.getSource()).getUniqueId(),
          event.getIdentifier().toString(),
          event.getData());
    }
  }
}
