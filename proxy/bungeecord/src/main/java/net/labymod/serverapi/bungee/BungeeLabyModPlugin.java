package net.labymod.serverapi.bungee;

import net.labymod.serverapi.api.LabyAPI;
import net.labymod.serverapi.api.LabyService;
import net.labymod.serverapi.api.payload.PayloadChannelRegistrar;
import net.labymod.serverapi.bungee.payload.channel.BungeeLegacyLabyModPayloadChannel;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class BungeeLabyModPlugin extends Plugin {

  private LabyService service;

  @Override
  public void onEnable() {
    this.service = new BungeeLabyService(this, this.getDescription().getVersion());
    LabyAPI.initialize(this.service);

    PayloadChannelRegistrar<String> payloadChannelRegistrar =
        this.service.getPayloadChannelRegistrar();
    payloadChannelRegistrar.registerModernChannelIdentifier("labymod3", "main");

    PluginManager pluginManager = this.getProxy().getPluginManager();

    pluginManager.registerListener(this, (Listener) this.service.getConnectionService());
    pluginManager.registerListener(this, new BungeeLegacyLabyModPayloadChannel(this.service, this));
  }

  @Override
  public void onDisable() {}

  public LabyService getService() {
    return service;
  }

  @Deprecated
  public String getPluginVersion() {
    return this.service.getVersion();
  }
}
