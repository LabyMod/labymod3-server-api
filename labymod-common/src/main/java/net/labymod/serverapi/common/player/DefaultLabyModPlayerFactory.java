package net.labymod.serverapi.common.player;

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

public class DefaultLabyModPlayerFactory<T> implements Factory<T> {

  /** {@inheritDoc} */
  @Override
  public LabyModPlayer<T> create(
      T player,
      String username,
      UUID uniqueId,
      String version,
      ChunkCachingProtocol chunkCachingProtocol,
      ShadowProtocol shadowProtocol,
      List<AddonExtension> addons,
      List<ModificationExtension> modifications) {
    return this.create(
        player,
        username,
        uniqueId,
        version,
        chunkCachingProtocol,
        shadowProtocol,
        addons,
        modifications,
        new ArrayList<>());
  }

  /** {@inheritDoc} */
  @Override
  public LabyModPlayer<T> create(
      T player,
      String username,
      UUID uniqueId,
      String version,
      ChunkCachingProtocol chunkCachingProtocol,
      ShadowProtocol shadowProtocol,
      List<AddonExtension> addons,
      List<ModificationExtension> modifications,
      List<PackageExtension> packages) {
    return new DefaultLabyModPlayer<>(
        player,
        username,
        uniqueId,
        version,
        chunkCachingProtocol,
        shadowProtocol,
        addons,
        modifications,
        packages);
  }
}
