package net.labymod.serverapi.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import net.labymod.serverapi.api.payload.PayloadChannelRegistrar;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.permission.PermissionService;
import net.labymod.serverapi.common.permission.DefaultPermissionFactory;
import net.labymod.serverapi.common.permission.DefaultPermissionService;
import net.labymod.serverapi.velocity.connection.VelocityConnectionService;
import net.labymod.serverapi.velocity.payload.VelocityPayloadChannelRegistrar;
import net.labymod.serverapi.velocity.payload.VelocityPayloadCommunicator;
import net.labymod.serverapi.velocity.payload.channel.VelocityLegacyLabyModPayloadChannel;

@Plugin(
    id = "labymod_server_api",
    name = "LabyMod Server API",
    version = "%version%",
    authors = {"LabyMedia GmbH"})
public class VelocityLabyModPlugin {

  private final ProxyServer proxyServer;

  private PayloadChannelRegistrar<ChannelIdentifier> payloadChannelRegistrar;
  private PayloadCommunicator payloadCommunicator;
  private PermissionService permissionService;

  @Inject
  public VelocityLabyModPlugin(ProxyServer proxyServer) {
    this.proxyServer = proxyServer;
  }

  @Subscribe
  public void initialize(ProxyInitializeEvent event) {
    // Initializes the labymod server api
    this.payloadChannelRegistrar =
        new VelocityPayloadChannelRegistrar(this.proxyServer.getChannelRegistrar());
    // LabyMod 3.0 Support
    this.payloadChannelRegistrar.registerModernLegacyChannelIdentifier("LMC");
    // LabyMod 4.0 Support
    this.payloadChannelRegistrar.registerModernChannelIdentifier("labymod", "main");

    this.payloadCommunicator =
        new VelocityPayloadCommunicator(this.proxyServer, this.payloadChannelRegistrar);

    this.permissionService =
        new DefaultPermissionService(
            this.payloadCommunicator, DefaultPermissionFactory.getInstance());

    this.proxyServer.getEventManager().register(this, new VelocityConnectionService(this));
    this.proxyServer.getEventManager().register(this, payloadCommunicator);
    this.proxyServer
        .getEventManager()
        .register(this, new VelocityLegacyLabyModPayloadChannel(this.proxyServer));
  }

  @Subscribe
  public void shutdown(ProxyShutdownEvent event) {
    this.payloadChannelRegistrar.unregisterModernLegacyChannelIdentifier("LMC");
    this.payloadChannelRegistrar.unregisterModernChannelIdentifier("labymod", "main");
  }

  public PermissionService getPermissionService() {
    return permissionService;
  }
}
