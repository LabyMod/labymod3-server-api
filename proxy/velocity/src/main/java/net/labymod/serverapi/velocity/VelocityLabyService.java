package net.labymod.serverapi.velocity;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import net.labymod.serverapi.api.connection.ConnectionService;
import net.labymod.serverapi.api.payload.PayloadChannelRegistrar;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.common.AbstractLabyService;
import net.labymod.serverapi.common.player.DefaultLabyModPlayerService;
import net.labymod.serverapi.velocity.connection.VelocityConnectionService;
import net.labymod.serverapi.velocity.payload.VelocityPayloadChannelRegistrar;
import net.labymod.serverapi.velocity.payload.VelocityPayloadCommunicator;

public class VelocityLabyService extends AbstractLabyService {

  private final ConnectionService<Player> connectionService;
  private final LabyModPlayerService<Player> playerService;
  private final PayloadCommunicator payloadCommunicator;
  private final PayloadChannelRegistrar<ChannelIdentifier> payloadChannelRegistrar;

  public VelocityLabyService(ProxyServer proxyServer) {
    this.connectionService = new VelocityConnectionService(this);
    this.playerService = new DefaultLabyModPlayerService<>();
    this.payloadCommunicator = new VelocityPayloadCommunicator(proxyServer, this);
    this.payloadChannelRegistrar = new VelocityPayloadChannelRegistrar(proxyServer);
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
