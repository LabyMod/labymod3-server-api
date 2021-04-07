package net.labymod.serverapi.bukkit;

import net.labymod.serverapi.api.LabyAPI;
import net.labymod.serverapi.api.LabyService;
import net.labymod.serverapi.api.payload.PayloadChannelRegistrar;
import net.labymod.serverapi.bukkit.payload.channel.BukkitLegacyLabyModPayloadChannel;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitLabyModPlugin extends JavaPlugin {

  private LabyService service;

  @Override
  public void onEnable() {
    this.service = new BukkitLabyService(this, getDescription().getVersion());
    LabyAPI.initialize(this.service);

    PayloadChannelRegistrar<String> payloadChannelRegistrar =
        this.service.getPayloadChannelRegistrar();
    payloadChannelRegistrar.registerModernChannelIdentifier("labymod3", "main");

    this.getServer()
        .getPluginManager()
        .registerEvents((Listener) this.service.getConnectionService(), this);
    this.getServer()
        .getPluginManager()
        .registerEvents(new BukkitLegacyLabyModPayloadChannel(this, this.service), this);
  }

  @Override
  public void onDisable() {}

  @Deprecated
  public String getPluginVersion() {
    return this.service.getVersion();
  }
}
