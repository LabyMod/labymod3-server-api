package net.labymod.serverapi.velocity.connection;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.proxy.Player;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.velocity.VelocityLabyModPlugin;
import net.labymod.serverapi.velocity.event.VelocityLabyModPlayerLoginEvent;
import net.labymod.serverapi.velocity.player.VelocityLabyModPlayerFactory;
import net.labymod.serverapi.velocity.player.VelocityLabyModPlayerService;

public class VelocityConnectionService {

  private final VelocityLabyModPlugin plugin;
  private final LabyModPlayer.Factory<Player> labyModPlayerFactory;
  private final LabyModPlayerService<Player> labyModPlayerService;

  public VelocityConnectionService(VelocityLabyModPlugin plugin) {
    this.plugin = plugin;
    this.labyModPlayerFactory = VelocityLabyModPlayerFactory.getInstance();
    this.labyModPlayerService = VelocityLabyModPlayerService.getInstance();
  }

  @Subscribe
  public void login(VelocityLabyModPlayerLoginEvent event) {
    Player player = event.getPlayer();
    this.labyModPlayerService.registerPlayer(
        this.labyModPlayerFactory.create(
            player,
            player.getUsername(),
            player.getUniqueId(),
            event.getVersion(),
            event.getChunkCachingProtocol(),
            event.getShadowProtocol(),
            event.getAddonExtensions(),
            event.getModificationExtensions()));

    this.plugin.getPermissionService().sendPermissions(player.getUniqueId());
  }

  @Subscribe
  public void disconnect(DisconnectEvent event) {
    this.labyModPlayerService.unregisterPlayerIf(
        player -> player.getUniqueId().equals(event.getPlayer().getUniqueId()));
  }
}
