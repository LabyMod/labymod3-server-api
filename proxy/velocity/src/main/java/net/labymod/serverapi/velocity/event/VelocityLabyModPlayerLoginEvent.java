package net.labymod.serverapi.velocity.event;

import com.velocitypowered.api.proxy.Player;
import java.util.ArrayList;
import java.util.List;
import net.labymod.serverapi.api.extension.AddonExtension;
import net.labymod.serverapi.api.extension.ModificationExtension;
import net.labymod.serverapi.api.extension.PackageExtension;
import net.labymod.serverapi.api.protocol.ChunkCachingProtocol;
import net.labymod.serverapi.api.protocol.ShadowProtocol;

/** Will be fired if a LabyMod player has connected to the proxy. */
public class VelocityLabyModPlayerLoginEvent {

  private final Player player;
  private final List<AddonExtension> addonExtensions;
  private final List<ModificationExtension> modificationExtensions;
  private final List<PackageExtension> packageExtensions;
  private final String version;
  private final ChunkCachingProtocol chunkCachingProtocol;
  private final ShadowProtocol shadowProtocol;

  public VelocityLabyModPlayerLoginEvent(
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

  public VelocityLabyModPlayerLoginEvent(
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
   * Retrieves the connected player playing with LabyMod.
   *
   * @return The connected player playing with LabyMod.
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Retrieves a collection with all installed addons of the connected player.
   *
   * @return A collection with all installed addons.
   */
  public List<AddonExtension> getAddonExtensions() {
    return addonExtensions;
  }

  /**
   * Retrieves a collection with all installed addons of the connected player.
   *
   * @return A collection with all installed modification.
   */
  public List<ModificationExtension> getModificationExtensions() {
    return modificationExtensions;
  }

  /**
   * Retrieves a collection with all installed packages of the connected player.
   *
   * @return A collection with all installed packages.
   */
  public List<PackageExtension> getPackageExtensions() {
    return packageExtensions;
  }

  /**
   * Retrieves the version of LabyMod from the connected player.
   *
   * @return The LabyMod version of the connected player.
   */
  public String getVersion() {
    return version;
  }

  /**
   * Retrieves the chunk caching protocol of the connected player.
   *
   * @return The connected player's chunk caching protocol.
   */
  public ChunkCachingProtocol getChunkCachingProtocol() {
    return chunkCachingProtocol;
  }

  /**
   * Retrieves the shadow protocol of the connected player.
   *
   * @return The connected player's shadow protocol.
   */
  public ShadowProtocol getShadowProtocol() {
    return shadowProtocol;
  }
}
