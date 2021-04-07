package net.labymod.serverapi.bungee.connection;

import java.util.List;
import java.util.UUID;
import net.labymod.serverapi.api.LabyService;
import net.labymod.serverapi.api.connection.ConnectionService;
import net.labymod.serverapi.api.extension.AddonExtension;
import net.labymod.serverapi.api.extension.ModificationExtension;
import net.labymod.serverapi.api.extension.PackageExtension;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.permission.PermissionService;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.api.protocol.ChunkCachingProtocol;
import net.labymod.serverapi.api.protocol.ShadowProtocol;
import net.labymod.serverapi.bungee.event.BungeeLabyModPlayerLoginEvent;
import net.labymod.serverapi.bungee.player.BungeeLabyModPlayerFactory;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class BungeeConnectionService implements Listener, ConnectionService<ProxiedPlayer> {

  private final PayloadCommunicator payloadCommunicator;
  private final PermissionService permissionService;
  private final LabyModPlayer.Factory<ProxiedPlayer> labyModPlayerFactory;
  private final LabyModPlayerService<ProxiedPlayer> labyModPlayerService;

  public BungeeConnectionService(LabyService service) {
    this.payloadCommunicator = service.getPayloadCommunicator();
    this.permissionService = service.getPermissionService();
    this.labyModPlayerFactory = new BungeeLabyModPlayerFactory();
    this.labyModPlayerService = service.getLabyPlayerService();
  }

  @EventHandler
  public void login(BungeeLabyModPlayerLoginEvent event) {
    this.login(
        event.getPlayer(),
        event.getPlayer().getName(),
        event.getPlayer().getUniqueId(),
        event.getVersion(),
        event.getChunkCachingProtocol(),
        event.getShadowProtocol(),
        event.getAddonExtensions(),
        event.getModificationExtensions());
  }

  @EventHandler
  public void disconnect(PlayerDisconnectEvent event) {
    this.disconnect(event.getPlayer().getUniqueId());
  }

  /** {@inheritDoc} */
  @Override
  public void login(
      ProxiedPlayer player,
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
            modifications,
            packages));

    this.payloadCommunicator.sendServerApiMessage(uniqueId);
    this.permissionService.sendPermissions(uniqueId);
  }

  /** {@inheritDoc} */
  @Override
  public void disconnect(UUID uniqueId) {
    this.labyModPlayerService.unregisterPlayerIf(player -> player.getUniqueId().equals(uniqueId));
  }
}
