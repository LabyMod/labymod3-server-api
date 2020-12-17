package net.labymod.serverapi.velocity;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import net.labymod.serverapi.api.payload.PayloadChannelRegistrar;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.common.connection.ConnectionService;
import net.labymod.serverapi.common.guice.LabyModInjector;
import net.labymod.serverapi.common.payload.DefaultLegacyLabyModPayloadChannel;
import net.labymod.serverapi.velocity.guice.LabyModVelocityModule;

@Plugin(
    id = "labymod_server_api",
    name = "LabyMod Server API",
    version = "2.0.0",
    authors = {"LabyMedia GmbH"})
public class VelocityLabyModPlugin {

  private final ProxyServer proxyServer;

  private final LabyModInjector labyModInjector;
  private PayloadChannelRegistrar<ChannelIdentifier> payloadChannelRegistrar;

  @Inject
  public VelocityLabyModPlugin(Injector injector, ProxyServer proxyServer) {
    this.labyModInjector = LabyModInjector.getInstance();
    this.labyModInjector.setInjector(injector);
    this.labyModInjector.addModule(new LabyModVelocityModule());
    this.proxyServer = proxyServer;
  }

  @Subscribe
  public void initialize(ProxyInitializeEvent event) {
    // Initializes the labymod server api
    this.payloadChannelRegistrar =
        this.labyModInjector.getInjectedInstance(
            new TypeLiteral<PayloadChannelRegistrar<ChannelIdentifier>>() {});
    // LabyMod 3.0 Support
    this.payloadChannelRegistrar.registerModernLegacyChannelIdentifier("LMC");
    // LabyMod 4.0 Support
    this.payloadChannelRegistrar.registerModernChannelIdentifier("labymod", "main");

    this.proxyServer
        .getEventManager()
        .register(this, this.labyModInjector.getInjectedInstance(ConnectionService.class));
    this.proxyServer
        .getEventManager()
        .register(this, this.labyModInjector.getInjectedInstance(PayloadCommunicator.class));
    this.proxyServer
        .getEventManager()
        .register(
            this,
            this.labyModInjector.getInjectedInstance(DefaultLegacyLabyModPayloadChannel.class));
  }

  @Subscribe
  public void shutdown(ProxyShutdownEvent event) {
    this.payloadChannelRegistrar.unregisterModernLegacyChannelIdentifier("LMC");
    this.payloadChannelRegistrar.unregisterModernChannelIdentifier("labymod", "main");
  }
}
