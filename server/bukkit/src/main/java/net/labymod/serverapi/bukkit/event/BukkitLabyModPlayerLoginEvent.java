package net.labymod.serverapi.bukkit.event;

import java.util.ArrayList;
import java.util.List;
import net.labymod.serverapi.api.extension.AddonExtension;
import net.labymod.serverapi.api.extension.ModificationExtension;
import net.labymod.serverapi.api.extension.PackageExtension;
import net.labymod.serverapi.api.protocol.ChunkCachingProtocol;
import net.labymod.serverapi.api.protocol.ShadowProtocol;
import org.bukkit.entity.Player;

/** This event is fired when a player joins the server with the LabyMod. */
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

  /**
   * Retrieves the player that is connected to the server with LabyMod.
   *
   * @return The player that is connected to the server with LabyMod.
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Retrieves a collection with all installed addons of the connected player.
   *
   * @return A collection with all installed addons of the connected player.
   */
  public List<AddonExtension> getAddonExtensions() {
    return addonExtensions;
  }

  /**
   * Retrieves a collection with all installed modifications of the connected player.
   *
   * @return A collection with all installed modifications of the connected player.
   */
  public List<ModificationExtension> getModificationExtensions() {
    return modificationExtensions;
  }

  /**
   * Retrieves a collection with all installed packages of the connected player.
   *
   * @return A collection with all installed packages of the connected player.
   */
  public List<PackageExtension> getPackageExtensions() {
    return packageExtensions;
  }

  /**
   * Retrieves the version of LabyMod that the connected player is playing.
   *
   * @return The version of LabyMod that the connected player is playing.
   */
  public String getVersion() {
    return version;
  }

  /**
   * Retrieves information about the chunk caching protocol of the player.
   *
   * @return Information about the chuck caching protocol.
   */
  public ChunkCachingProtocol getChunkCachingProtocol() {
    return chunkCachingProtocol;
  }

  /**
   * Retrieves information about the shadow protocol of the player.
   *
   * @return Information about the shadow protocol.
   */
  public ShadowProtocol getShadowProtocol() {
    return shadowProtocol;
  }
}
