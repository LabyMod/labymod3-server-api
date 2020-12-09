package net.labymod.serverapi.bungee.connection;

import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.bungee.BungeeLabyModPlugin;
import net.labymod.serverapi.bungee.event.BungeeLabyModPlayerLoginEvent;
import net.labymod.serverapi.bungee.player.BungeeLabyModPlayerFactory;
import net.labymod.serverapi.bungee.player.BungeeLabyModPlayerService;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class BungeeConnectionService implements Listener {

  private final BungeeLabyModPlugin plugin;
  private final LabyModPlayer.Factory<ProxiedPlayer> labyModPlayerFactory;
  private final LabyModPlayerService<ProxiedPlayer> labyModPlayerService;

  public BungeeConnectionService(BungeeLabyModPlugin plugin) {
    this.plugin = plugin;
    this.labyModPlayerFactory = BungeeLabyModPlayerFactory.getInstance();
    this.labyModPlayerService = BungeeLabyModPlayerService.getInstance();
  }

  @EventHandler
  public void login(BungeeLabyModPlayerLoginEvent event) {
    ProxiedPlayer player = event.getPlayer();
    this.labyModPlayerService.registerPlayer(
        this.labyModPlayerFactory.create(
            player,
            null,
            null,
            event.getVersion(),
            event.getChunkCachingProtocol(),
            event.getShadowProtocol(),
            event.getAddonExtensions(),
            event.getModificationExtensions()));

    this.plugin.getPermissionService().sendPermissions(player.getUniqueId());
  }

  @EventHandler
  public void disconnect(PlayerDisconnectEvent event) {
    this.labyModPlayerService.unregisterPlayerIf(
        player -> player.getUniqueId().equals(event.getPlayer().getUniqueId()));
  }
}
