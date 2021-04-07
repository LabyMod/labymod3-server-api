package net.labymod.serverapi.velocity.connection;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.proxy.Player;
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
import net.labymod.serverapi.common.player.DefaultLabyModPlayerFactory;
import net.labymod.serverapi.velocity.event.VelocityLabyModPlayerLoginEvent;

import java.util.List;
import java.util.UUID;

public class VelocityConnectionService implements ConnectionService<Player> {

  private final PayloadCommunicator payloadCommunicator;
  private final PermissionService permissionService;
  private final LabyModPlayer.Factory<Player> labyModPlayerFactory;
  private final LabyModPlayerService<Player> labyModPlayerService;

  public VelocityConnectionService(LabyService service) {
    this.payloadCommunicator = service.getPayloadCommunicator();
    this.permissionService = service.getPermissionService();
    this.labyModPlayerFactory = new DefaultLabyModPlayerFactory<>();
    this.labyModPlayerService = service.getLabyPlayerService();
  }

  @Subscribe
  public void login(VelocityLabyModPlayerLoginEvent event) {
    Player player = event.getPlayer();
    this.login(
        player,
        player.getUsername(),
        player.getUniqueId(),
        event.getVersion(),
        event.getChunkCachingProtocol(),
        event.getShadowProtocol(),
        event.getAddonExtensions(),
        event.getModificationExtensions());
  }

  @Subscribe
  public void disconnect(DisconnectEvent event) {
    this.disconnect(event.getPlayer().getUniqueId());
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

    this.payloadCommunicator.sendServerApiMessage(uniqueId);
    this.permissionService.sendPermissions(uniqueId);
  }

  /** {@inheritDoc} */
  @Override
  public void disconnect(UUID uniqueId) {
    this.labyModPlayerService.unregisterPlayerIf(player -> player.getUniqueId().equals(uniqueId));
  }
}
