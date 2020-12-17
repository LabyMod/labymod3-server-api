package net.labymod.serverapi.bungee;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import net.labymod.serverapi.api.payload.PayloadChannelRegistrar;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.bungee.guice.LabyModBungeeCordModule;
import net.labymod.serverapi.api.connection.ConnectionService;
import net.labymod.serverapi.common.guice.LabyModInjector;
import net.labymod.serverapi.common.payload.DefaultLegacyLabyModPayloadChannel;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

@Singleton
public class BungeeLabyModPlugin extends Plugin {

  private final LabyModInjector labyModInjector;

  public BungeeLabyModPlugin() {
    this.labyModInjector = LabyModInjector.getInstance();
    this.labyModInjector.addModule(
        new AbstractModule() {
          @Override
          protected void configure() {
            this.bind(Plugin.class).toInstance(BungeeLabyModPlugin.this);
            this.bind(BungeeLabyModPlugin.class).toInstance(BungeeLabyModPlugin.this);
          }
        });
    this.labyModInjector.addModule(new LabyModBungeeCordModule());
  }

  @Override
  public void onEnable() {
    PayloadChannelRegistrar<String> payloadChannelRegistrar =
        this.labyModInjector.getInjectedInstance(
            new TypeLiteral<PayloadChannelRegistrar<String>>() {});
    payloadChannelRegistrar.registerModernLegacyChannelIdentifier("LMC");
    payloadChannelRegistrar.registerModernChannelIdentifier("labymod", "main");

    PluginManager pluginManager = this.getProxy().getPluginManager();

    pluginManager.registerListener(
        this,
        (Listener)
            this.labyModInjector.getInjectedInstance(
                new TypeLiteral<ConnectionService<ProxiedPlayer>>() {}));
    pluginManager.registerListener(
        this, (Listener) this.labyModInjector.getInjectedInstance(PayloadCommunicator.class));
    pluginManager.registerListener(
        this,
        (Listener)
            this.labyModInjector.getInjectedInstance(DefaultLegacyLabyModPayloadChannel.class));
  }

  @Override
  public void onDisable() {}
}
