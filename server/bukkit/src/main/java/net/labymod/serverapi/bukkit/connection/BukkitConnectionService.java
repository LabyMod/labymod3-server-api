package net.labymod.serverapi.bukkit.connection;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.List;
import java.util.UUID;
import net.labymod.serverapi.api.extension.AddonExtension;
import net.labymod.serverapi.api.extension.ModificationExtension;
import net.labymod.serverapi.api.extension.PackageExtension;
import net.labymod.serverapi.api.permission.PermissionService;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.player.LabyModPlayer.Factory;
import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.api.protocol.ChunkCachingProtocol;
import net.labymod.serverapi.api.protocol.ShadowProtocol;
import net.labymod.serverapi.bukkit.event.BukkitLabyModPlayerLoginEvent;
import net.labymod.serverapi.api.connection.ConnectionService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Singleton
public class BukkitConnectionService implements ConnectionService<Player>, Listener {

  private final PermissionService permissionService;
  private final LabyModPlayer.Factory<Player> labyModPlayerFactory;
  private final LabyModPlayerService<Player> labyModPlayerService;

  @Inject
  private BukkitConnectionService(
      PermissionService permissionService,
      Factory<Player> labyModPlayerFactory,
      LabyModPlayerService<Player> labyModPlayerService) {
    this.permissionService = permissionService;
    this.labyModPlayerFactory = labyModPlayerFactory;
    this.labyModPlayerService = labyModPlayerService;
  }

  @EventHandler
  public void login(BukkitLabyModPlayerLoginEvent event) {
    Player player = event.getPlayer();
    this.login(
        player,
        player.getName(),
        player.getUniqueId(),
        event.getVersion(),
        event.getChunkCachingProtocol(),
        event.getShadowProtocol(),
        event.getAddonExtensions(),
        event.getModificationExtensions());
  }

  /** {@inheritDoc} */
  @Override
  public void login(
      Player player,
      String username,
      UUID uniqueId,
      String version,
      ChunkCachingProtocol chunkCachingProtocol,
      ShadowProtocol shadowProtocol,
      List<AddonExtension> addons,
      List<ModificationExtension> modifications,
      List<PackageExtension> packages) {
    this.labyModPlayerService.registerPlayer(
        this.labyModPlayerFactory.create(
            player,
            username,
            uniqueId,
            version,
            chunkCachingProtocol,
            shadowProtocol,
            addons,
            modifications));

    this.permissionService.sendPermissions(uniqueId);
  }

  /** {@inheritDoc} */
  @Override
  public void disconnect(UUID uniqueId) {
    this.labyModPlayerService.unregisterPlayerIf(player -> player.getUniqueId().equals(uniqueId));
  }
}
