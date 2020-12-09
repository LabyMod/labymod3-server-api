package net.labymod.serverapi.bungee;

import net.labymod.serverapi.api.payload.PayloadChannelRegistrar;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.permission.PermissionService;
import net.labymod.serverapi.bungee.connection.BungeeConnectionService;
import net.labymod.serverapi.bungee.payload.BungeePayloadChannelRegistrar;
import net.labymod.serverapi.bungee.payload.BungeePayloadCommunicator;
import net.labymod.serverapi.bungee.payload.channel.BungeeLegacyLabyModPayloadChannel;
import net.labymod.serverapi.common.permission.DefaultPermissionFactory;
import net.labymod.serverapi.common.permission.DefaultPermissionService;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class BungeeLabyModPlugin extends Plugin {

  private PayloadChannelRegistrar<String> payloadChannelRegistrar;
  private PayloadCommunicator payloadCommunicator;
  private PermissionService permissionService;

  @Override
  public void onEnable() {
    this.payloadChannelRegistrar = new BungeePayloadChannelRegistrar(this.getProxy());
    this.payloadChannelRegistrar.registerModernLegacyChannelIdentifier("LMC");
    this.payloadChannelRegistrar.registerModernChannelIdentifier("labymod", "main");

    this.payloadCommunicator =
        new BungeePayloadCommunicator(this.payloadChannelRegistrar, this.getProxy());

    this.permissionService =
        new DefaultPermissionService(
            this.payloadCommunicator, DefaultPermissionFactory.getInstance());

    PluginManager pluginManager = this.getProxy().getPluginManager();

    pluginManager.registerListener(this, new BungeeConnectionService(this));
    pluginManager.registerListener(this, (Listener) this.payloadCommunicator);
    pluginManager.registerListener(this, new BungeeLegacyLabyModPayloadChannel(this.getProxy()));
  }

  @Override
  public void onDisable() {}

  public PermissionService getPermissionService() {
    return permissionService;
  }
}
