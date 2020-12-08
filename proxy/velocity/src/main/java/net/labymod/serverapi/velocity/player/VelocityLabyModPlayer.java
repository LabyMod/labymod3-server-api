package net.labymod.serverapi.velocity.player;

import com.velocitypowered.api.proxy.Player;
import java.util.List;
import net.labymod.serverapi.api.extension.AddonExtension;
import net.labymod.serverapi.api.extension.ModificationExtension;
import net.labymod.serverapi.api.extension.PackageExtension;
import net.labymod.serverapi.api.protocol.ChunkCachingProtocol;
import net.labymod.serverapi.api.protocol.ShadowProtocol;
import net.labymod.serverapi.common.player.DefaultLabyModPlayer;

public class VelocityLabyModPlayer extends DefaultLabyModPlayer<Player> {

  public VelocityLabyModPlayer(
      Player player,
      String version,
      ChunkCachingProtocol chunkCachingProtocol,
      ShadowProtocol shadowProtocol,
      List<AddonExtension> addons,
      List<ModificationExtension> modifications,
      List<PackageExtension> packages) {
    super(
        player,
        player.getUsername(),
        player.getUniqueId(),
        version,
        chunkCachingProtocol,
        shadowProtocol,
        addons,
        modifications,
        packages);
  }
}
