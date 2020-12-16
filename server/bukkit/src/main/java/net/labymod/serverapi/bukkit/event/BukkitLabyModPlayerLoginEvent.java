package net.labymod.serverapi.bukkit.event;

import java.util.ArrayList;
import java.util.List;
import net.labymod.serverapi.api.extension.AddonExtension;
import net.labymod.serverapi.api.extension.ModificationExtension;
import net.labymod.serverapi.api.extension.PackageExtension;
import net.labymod.serverapi.api.protocol.ChunkCachingProtocol;
import net.labymod.serverapi.api.protocol.ShadowProtocol;
import org.bukkit.entity.Player;

public class BukkitLabyModPlayerLoginEvent extends BukkitLabyModEvent {

  private final Player player;
  private final List<AddonExtension> addonExtensions;
  private final List<ModificationExtension> modificationExtensions;
  private final List<PackageExtension> packageExtensions;
  private final String version;
  private final ChunkCachingProtocol chunkCachingProtocol;
  private final ShadowProtocol shadowProtocol;

  public BukkitLabyModPlayerLoginEvent(
      Player player,
      List<AddonExtension> addonExtensions,
      List<ModificationExtension> modificationExtensions,
      String version,
      ChunkCachingProtocol chunkCachingProtocol,
      ShadowProtocol shadowProtocol) {
    this(
        player,
        addonExtensions,
        modificationExtensions,
        new ArrayList<>(),
        version,
        chunkCachingProtocol,
        shadowProtocol);
  }

  public BukkitLabyModPlayerLoginEvent(
      Player player,
      List<AddonExtension> addonExtensions,
      List<ModificationExtension> modificationExtensions,
      List<PackageExtension> packageExtensions,
      String version,
      ChunkCachingProtocol chunkCachingProtocol,
      ShadowProtocol shadowProtocol) {
    this.player = player;
    this.addonExtensions = addonExtensions;
    this.modificationExtensions = modificationExtensions;
    this.packageExtensions = packageExtensions;
    this.version = version;
    this.chunkCachingProtocol = chunkCachingProtocol;
    this.shadowProtocol = shadowProtocol;
  }

  public Player getPlayer() {
    return player;
  }

  public List<AddonExtension> getAddonExtensions() {
    return addonExtensions;
  }

  public List<ModificationExtension> getModificationExtensions() {
    return modificationExtensions;
  }

  public List<PackageExtension> getPackageExtensions() {
    return packageExtensions;
  }

  public String getVersion() {
    return version;
  }

  public ChunkCachingProtocol getChunkCachingProtocol() {
    return chunkCachingProtocol;
  }

  public ShadowProtocol getShadowProtocol() {
    return shadowProtocol;
  }
}
