package net.labymod.serverapi.bungee;

import net.labymod.serverapi.api.LabyDebugger;
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

  private final LabyDebugger labyDebugger;
  private final ConnectionService<ProxiedPlayer> connectionService;
  private final LabyModPlayerService<ProxiedPlayer> playerService;
  private final PayloadCommunicator payloadCommunicator;
  private final PayloadChannelRegistrar<String> payloadChannelRegistrar;

  private final String pluginVersion;

  public BungeeLabyService(BungeeLabyModPlugin plugin, String pluginVersion) {
    this.labyDebugger = new BungeeLabyDebugger(plugin);
    this.pluginVersion = pluginVersion;
    this.playerService = new DefaultLabyModPlayerService<>();
    this.payloadCommunicator = new BungeePayloadCommunicator(this, plugin);
    this.payloadChannelRegistrar = new BungeePayloadChannelRegistrar(plugin);

    ((BungeePayloadCommunicator) this.payloadCommunicator)
        .setPayloadChannelRegistrar(this.payloadChannelRegistrar);
    this.initialize();
    this.connectionService = new BungeeConnectionService(this);
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
