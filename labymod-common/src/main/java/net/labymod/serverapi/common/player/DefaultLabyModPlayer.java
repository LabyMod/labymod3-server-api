package net.labymod.serverapi.common.player;

import java.util.List;
import java.util.UUID;
import net.labymod.serverapi.api.extension.AddonExtension;
import net.labymod.serverapi.api.extension.ModificationExtension;
import net.labymod.serverapi.api.extension.PackageExtension;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.protocol.ChunkCachingProtocol;
import net.labymod.serverapi.api.protocol.ShadowProtocol;

public class DefaultLabyModPlayer<T> implements LabyModPlayer<T> {

  private final T player;
  private final String username;
  private final UUID uniqueId;
  private final String version;
  private final ChunkCachingProtocol chunkCachingProtocol;
  private final ShadowProtocol shadowProtocol;
  private final List<AddonExtension> addons;
  private final List<ModificationExtension> modifications;
  private final List<PackageExtension> packages;

  public DefaultLabyModPlayer(
      T player,
      String username,
      UUID uniqueId,
      String version,
      ChunkCachingProtocol chunkCachingProtocol,
      ShadowProtocol shadowProtocol,
      List<AddonExtension> addons,
      List<ModificationExtension> modifications,
      List<PackageExtension> packages) {
    this.player = player;
    this.username = username;
    this.uniqueId = uniqueId;
    this.version = version;
    this.chunkCachingProtocol = chunkCachingProtocol;
    this.shadowProtocol = shadowProtocol;
    this.addons = addons;
    this.modifications = modifications;
    this.packages = packages;
  }

  /** {@inheritDoc} */
  @Override
  public T getPlayer() {
    return this.player;
  }

  /** {@inheritDoc} */
  @Override
  public String getUsername() {
    return this.username;
  }

  /** {@inheritDoc} */
  @Override
  public UUID getUniqueId() {
    return this.uniqueId;
  }

  /** {@inheritDoc} */
  @Override
  public String getVersion() {
    return this.version;
  }

  /** {@inheritDoc} */
  @Override
  public ChunkCachingProtocol getChunkCachingProtocol() {
    return this.chunkCachingProtocol;
  }

  /** {@inheritDoc} */
  @Override
  public ShadowProtocol getShadowProtocol() {
    return this.shadowProtocol;
  }

  /** {@inheritDoc} */
  @Override
  public List<AddonExtension> getAddons() {
    return this.addons;
  }

  /** {@inheritDoc} */
  @Override
  public List<ModificationExtension> getModifications() {
    return this.modifications;
  }

  /** {@inheritDoc} */
  @Override
  public List<PackageExtension> getPackages() {
    return this.packages;
  }
}
