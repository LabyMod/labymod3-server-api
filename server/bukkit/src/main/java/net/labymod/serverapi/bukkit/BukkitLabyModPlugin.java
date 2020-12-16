package net.labymod.serverapi.bukkit;

import net.labymod.serverapi.api.payload.PayloadChannelRegistrar;
import net.labymod.serverapi.bukkit.payload.BukkitPayloadChannelRegistrar;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitLabyModPlugin extends JavaPlugin {

  private PayloadChannelRegistrar<String> payloadChannelRegistrar;

  @Override
  public void onEnable() {
    this.payloadChannelRegistrar = new BukkitPayloadChannelRegistrar(this);

  }

  @Override
  public void onDisable() {}
}
