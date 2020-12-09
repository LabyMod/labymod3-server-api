package net.labymod.serverapi.bungee.player;

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
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeLabyModPlayerFactory extends DefaultLabyModPlayerFactory<ProxiedPlayer> {

  private static final LabyModPlayer.Factory<ProxiedPlayer> INSTANCE =
      new BungeeLabyModPlayerFactory();

  private BungeeLabyModPlayerFactory() {}

  public static Factory<ProxiedPlayer> getInstance() {
    return INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public LabyModPlayer<ProxiedPlayer> create(
      ProxiedPlayer player,
      String username,
      UUID uniqueId,
      String version,
      ChunkCachingProtocol chunkCachingProtocol,
      ShadowProtocol shadowProtocol,
      List<AddonExtension> addons,
      List<ModificationExtension> modifications) {
    return this.create(
        player,
        player.getName(),
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
  public LabyModPlayer<ProxiedPlayer> create(
      ProxiedPlayer player,
      String username,
      UUID uniqueId,
      String version,
      ChunkCachingProtocol chunkCachingProtocol,
      ShadowProtocol shadowProtocol,
      List<AddonExtension> addons,
      List<ModificationExtension> modifications,
      List<PackageExtension> packages) {
    return new BungeeLabyModPlayer(
        player, version, chunkCachingProtocol, shadowProtocol, addons, modifications, packages);
  }
}
