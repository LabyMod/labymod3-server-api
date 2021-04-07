package net.labymod.serverapi.velocity;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import net.labymod.serverapi.api.LabyDebugger;
import net.labymod.serverapi.api.connection.ConnectionService;
import net.labymod.serverapi.api.payload.PayloadChannelRegistrar;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.common.AbstractLabyService;
import net.labymod.serverapi.common.player.DefaultLabyModPlayerService;
import net.labymod.serverapi.velocity.connection.VelocityConnectionService;
import net.labymod.serverapi.velocity.payload.VelocityPayloadChannelRegistrar;
import net.labymod.serverapi.velocity.payload.VelocityPayloadCommunicator;
import org.slf4j.Logger;

public class VelocityLabyService extends AbstractLabyService {

  private final LabyDebugger labyDebugger;
  private final ConnectionService<Player> connectionService;
  private final LabyModPlayerService<Player> playerService;
  private final PayloadCommunicator payloadCommunicator;
  private final PayloadChannelRegistrar<ChannelIdentifier> payloadChannelRegistrar;

  private final String pluginVersion;

  public VelocityLabyService(ProxyServer proxyServer, Logger logger, String pluginVersion) {
    this.labyDebugger = new VelocityLabyDebugger(logger);

    this.pluginVersion = pluginVersion;
    this.playerService = new DefaultLabyModPlayerService<>();
    this.payloadCommunicator = new VelocityPayloadCommunicator(proxyServer, this);
    this.payloadChannelRegistrar = new VelocityPayloadChannelRegistrar(proxyServer);

    ((VelocityPayloadCommunicator) this.payloadCommunicator)
        .setPayloadChannelRegistrar(this.payloadChannelRegistrar);
    this.initialize();
    this.connectionService = new VelocityConnectionService(this);
  }

  /** {@inheritDoc} */
  @Override
  public LabyDebugger getLabyDebugger() {
    return this.labyDebugger;
  }

  /** {@inheritDoc} */
  @Override
  public String getVersion() {
    return this.pluginVersion;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public <P> LabyModPlayerService<P> getLabyPlayerService() {
    return (LabyModPlayerService<P>) this.playerService;
  }

  /** {@inheritDoc} */
  @Override
  public PayloadCommunicator getPayloadCommunicator() {
    return this.payloadCommunicator;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public <P> PayloadChannelRegistrar<P> getPayloadChannelRegistrar() {
    return (PayloadChannelRegistrar<P>) this.payloadChannelRegistrar;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public <P> ConnectionService<P> getConnectionService() {
    return (ConnectionService<P>) this.connectionService;
  }
}
