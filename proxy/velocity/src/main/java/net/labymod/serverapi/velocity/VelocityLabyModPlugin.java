package net.labymod.serverapi.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import net.labymod.serverapi.api.payload.PayloadChannelRegistrar;
import net.labymod.serverapi.velocity.payload.VelocityPayloadChannelRegistrar;
import org.slf4j.Logger;

@Plugin(id = "labymod_server_api", name = "LabyMod Server API", version = "%version%")
public class VelocityLabyModPlugin {

  private final Logger logger;
  private final ProxyServer proxyServer;

  private PayloadChannelRegistrar payloadChannelRegistrar;

  @Inject
  public VelocityLabyModPlugin(Logger logger, ProxyServer proxyServer) {
    this.logger = logger;
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
  }

  @Subscribe
  public void shutdown(ProxyShutdownEvent event) {
    this.payloadChannelRegistrar.unregisterModernLegacyChannelIdentifier("LMC");
    this.payloadChannelRegistrar.unregisterModernChannelIdentifier("labymod", "main");
  }
}
