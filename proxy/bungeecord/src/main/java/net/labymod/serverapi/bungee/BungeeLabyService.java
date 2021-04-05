package net.labymod.serverapi.bungee;

import net.labymod.serverapi.api.connection.ConnectionService;
import net.labymod.serverapi.api.payload.PayloadChannelRegistrar;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.bungee.connection.BungeeConnectionService;
import net.labymod.serverapi.bungee.payload.BungeePayloadChannelRegistrar;
import net.labymod.serverapi.bungee.payload.BungeePayloadCommunicator;
import net.labymod.serverapi.common.AbstractLabyService;
import net.labymod.serverapi.common.player.DefaultLabyModPlayerService;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeLabyService extends AbstractLabyService {

  private final ConnectionService<ProxiedPlayer> connectionService;
  private final LabyModPlayerService<ProxiedPlayer> playerService;
  private final PayloadCommunicator payloadCommunicator;
  private final PayloadChannelRegistrar<String> payloadChannelRegistrar;

  public BungeeLabyService(BungeeLabyModPlugin plugin) {
    this.playerService = new DefaultLabyModPlayerService<>();
    this.payloadCommunicator = new BungeePayloadCommunicator(this, plugin);
    this.payloadChannelRegistrar = new BungeePayloadChannelRegistrar(plugin);

    ((BungeePayloadCommunicator) this.payloadCommunicator)
        .setPayloadChannelRegistrar(this.payloadChannelRegistrar);
    this.initialize();
    this.connectionService = new BungeeConnectionService(this);
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
