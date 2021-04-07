package net.labymod.serverapi.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import java.util.Optional;
import net.labymod.serverapi.api.LabyAPI;
import net.labymod.serverapi.api.LabyService;
import net.labymod.serverapi.api.payload.PayloadChannelRegistrar;
import net.labymod.serverapi.velocity.payload.channel.VelocityLegacyLabyModPayloadChannel;
import org.slf4j.Logger;

public class VelocityLabyModPlugin {

  private final ProxyServer proxyServer;
  private final Logger logger;
  private LabyService service;
  private PayloadChannelRegistrar<ChannelIdentifier> payloadChannelRegistrar;
  private String pluginVersion;

  @Inject
  public VelocityLabyModPlugin(ProxyServer proxyServer, Logger logger) {
    this.proxyServer = proxyServer;
    this.logger = logger;
  }

  @Subscribe
  public void initialize(ProxyInitializeEvent event) {
    this.proxyServer
        .getPluginManager()
        .getPlugin("laby_api")
        .ifPresent(
            plugin -> {
              Optional<String> versionOptional = plugin.getDescription().getVersion();
              boolean present = versionOptional.isPresent();
              if (present) {
                this.pluginVersion = versionOptional.get();
              }
            });

    this.service = new VelocityLabyService(this.proxyServer, this.logger, this.pluginVersion);
    LabyAPI.initialize(this.service);

    // Initializes the labymod server api
    this.payloadChannelRegistrar = this.service.getPayloadChannelRegistrar();
    // LabyMod 3.0 Support
    this.payloadChannelRegistrar.registerModernChannelIdentifier("labymod3", "main");

    this.proxyServer.getEventManager().register(this, this.service.getConnectionService());
    this.proxyServer.getEventManager().register(this, this.service.getPayloadCommunicator());
    this.proxyServer
        .getEventManager()
        .register(this, new VelocityLegacyLabyModPayloadChannel(this.proxyServer, this.service));
  }

  @Subscribe
  public void shutdown(ProxyShutdownEvent event) {
    this.payloadChannelRegistrar.unregisterModernChannelIdentifier("labymod3", "main");
  }

  @Deprecated
  public String getPluginVersion() {
    return this.service.getVersion();
  }
}
