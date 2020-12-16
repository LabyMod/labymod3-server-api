package net.labymod.serverapi.bukkit.payload;

import java.util.UUID;
import net.labymod.serverapi.api.payload.PayloadChannelRegistrar;
import net.labymod.serverapi.api.payload.PayloadChannelType;
import net.labymod.serverapi.bukkit.BukkitLabyModPlugin;
import net.labymod.serverapi.bukkit.event.BukkitReceivePayloadEvent;
import net.labymod.serverapi.bukkit.event.BukkitSendPayloadEvent;
import net.labymod.serverapi.bukkit.payload.transmitter.BukkitPayloadTransmitter;
import net.labymod.serverapi.bukkit.player.BukkitLabyModPlayerService;
import net.labymod.serverapi.common.payload.DefaultPayloadCommunicator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class BukkitPayloadCommunicator extends DefaultPayloadCommunicator
    implements PluginMessageListener {

  private final BukkitLabyModPlugin plugin;
  private final PayloadChannelRegistrar<String> payloadChannelRegistrar;

  public BukkitPayloadCommunicator(
      BukkitLabyModPlugin plugin, PayloadChannelRegistrar<String> payloadChannelRegistrar) {
    super(BukkitLabyModPlayerService.getInstance());
    this.plugin = plugin;
    this.payloadChannelRegistrar = payloadChannelRegistrar;
  }

  @Override
  public void send(UUID uniqueId, String identifier, byte[] payload) {
    Player player = this.plugin.getServer().getPlayer(uniqueId);

    if (player == null) {
      return;
    }

    PayloadChannelType channelType =
        identifier.contains(":") ? PayloadChannelType.MODERN : PayloadChannelType.LEGACY;

    this.payloadChannelRegistrar.getChannelIdentifiers().get(channelType).stream()
        .filter(channelIdentifier -> channelIdentifier.equals(identifier))
        .forEach(
            channelIdentifier -> {
              BukkitSendPayloadEvent bukkitSendPayloadEvent =
                  new BukkitSendPayloadEvent(player, channelIdentifier, payload);
              this.plugin.getServer().getPluginManager().callEvent(bukkitSendPayloadEvent);

              if (bukkitSendPayloadEvent.isCancelled()) {
                return;
              }

              BukkitPayloadTransmitter.transmitPayload(
                  player,
                  bukkitSendPayloadEvent.getChannelIdentifier(),
                  bukkitSendPayloadEvent.getPayload());
            });
  }

  @Override
  public void receive(UUID uniqueId, String identifier, byte[] payload) {
    this.plugin
        .getServer()
        .getPluginManager()
        .callEvent(new BukkitReceivePayloadEvent(uniqueId, identifier, payload));
  }

  @Override
  public void onPluginMessageReceived(String channel, Player player, byte[] message) {
    this.receive(player.getUniqueId(), channel, message);
  }
}
