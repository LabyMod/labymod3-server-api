package net.labymod.serverapi.bukkit;

import net.labymod.serverapi.api.connection.ConnectionService;
import net.labymod.serverapi.api.payload.PayloadChannelRegistrar;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.bukkit.connection.BukkitConnectionService;
import net.labymod.serverapi.bukkit.payload.BukkitPayloadChannelRegistrar;
import net.labymod.serverapi.bukkit.payload.BukkitPayloadCommunicator;
import net.labymod.serverapi.common.AbstractLabyService;
import net.labymod.serverapi.common.player.DefaultLabyModPlayerService;
import org.bukkit.entity.Player;

public class BukkitLabyService extends AbstractLabyService {

  private final ConnectionService<Player> connectionService;
  private final LabyModPlayerService<Player> playerService;
  private final PayloadCommunicator payloadCommunicator;
  private final PayloadChannelRegistrar<String> payloadChannelRegistrar;

  public BukkitLabyService(BukkitLabyModPlugin plugin) {
    this.connectionService = new BukkitConnectionService(this);
    this.playerService = new DefaultLabyModPlayerService<>();
    this.payloadCommunicator = new BukkitPayloadCommunicator(plugin, this);
    this.payloadChannelRegistrar = new BukkitPayloadChannelRegistrar(plugin, this);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <P> LabyModPlayerService<P> getLabyPlayerService() {
    return (LabyModPlayerService<P>) this.playerService;
  }

  @Override
  public PayloadCommunicator getPayloadCommunicator() {
    return this.payloadCommunicator;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <P> PayloadChannelRegistrar<P> getPayloadChannelRegistrar() {
    return (PayloadChannelRegistrar<P>) this.payloadChannelRegistrar;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <P> ConnectionService<P> getConnectionService() {
    return (ConnectionService<P>) this.connectionService;
  }
}
