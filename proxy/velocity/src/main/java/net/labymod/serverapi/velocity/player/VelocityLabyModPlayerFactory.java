package net.labymod.serverapi.velocity.player;

import com.google.inject.Singleton;
import com.velocitypowered.api.proxy.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.labymod.serverapi.api.extension.AddonExtension;
import net.labymod.serverapi.api.extension.ModificationExtension;
import net.labymod.serverapi.api.extension.PackageExtension;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.player.LabyModPlayer.Factory;
import net.labymod.serverapi.api.protocol.ChunkCachingProtocol;
import net.labymod.serverapi.api.protocol.ShadowProtocol;
import net.labymod.serverapi.common.player.DefaultLabyModPlayerFactory;

@Singleton
public class VelocityLabyModPlayerFactory extends DefaultLabyModPlayerFactory<Player> {

  /** {@inheritDoc} */
  @Override
  public LabyModPlayer<Player> create(
      Player player,
      String username,
      UUID uniqueId,
      String version,
      ChunkCachingProtocol chunkCachingProtocol,
      ShadowProtocol shadowProtocol,
      List<AddonExtension> addons,
      List<ModificationExtension> modifications) {
    return this.create(
        player,
        player.getUsername(),
        player.getUniqueId(),
        version,
        chunkCachingProtocol,
        shadowProtocol,
        addons,
        modifications,
        new ArrayList<>());
  }

  /** {@inheritDoc} */
  @Override
  public LabyModPlayer<Player> create(
      Player player,
      String username,
      UUID uniqueId,
      String version,
      ChunkCachingProtocol chunkCachingProtocol,
      ShadowProtocol shadowProtocol,
      List<AddonExtension> addons,
      List<ModificationExtension> modifications,
      List<PackageExtension> packages) {
    return new VelocityLabyModPlayer(
        player, version, chunkCachingProtocol, shadowProtocol, addons, modifications, packages);
  }
}
