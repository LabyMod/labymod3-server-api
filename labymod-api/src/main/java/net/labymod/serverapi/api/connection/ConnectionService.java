package net.labymod.serverapi.api.connection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.labymod.serverapi.api.extension.AddonExtension;
import net.labymod.serverapi.api.extension.ModificationExtension;
import net.labymod.serverapi.api.extension.PackageExtension;
import net.labymod.serverapi.api.protocol.ChunkCachingProtocol;
import net.labymod.serverapi.api.protocol.ShadowProtocol;

public interface ConnectionService<T> {

  /**
   * Called when a player connected to the server is playing with LabyMod.
   *
   * @param player The player who is connected to the LabyMod on the server.
   * @param username The username of the player.
   * @param uniqueId The unique identifier of the player.
   * @param version The version of LabyMod.
   * @param chunkCachingProtocol Information about the chuck caching protocol.
   * @param shadowProtocol Information about the shadow protocol.
   * @param addons A collection of all installed addons.
   * @param modifications A collection of all installed modifications.
   */
  default void login(
      T player,
      String username,
      UUID uniqueId,
      String version,
      ChunkCachingProtocol chunkCachingProtocol,
      ShadowProtocol shadowProtocol,
      List<AddonExtension> addons,
      List<ModificationExtension> modifications) {
    this.login(
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

  /**
   * Called when a player connected to the server is playing with LabyMod.
   *
   * @param player The player who is connected to the LabyMod on the server.
   * @param username The username of the player.
   * @param uniqueId The unique identifier of the player.
   * @param version The version of LabyMod.
   * @param chunkCachingProtocol Information about the chuck caching protocol.
   * @param shadowProtocol Information about the shadow protocol.
   * @param addons A collection of all installed addons.
   * @param modifications A collection of all installed modifications.
   * @param packages A collection of all installed packages.
   */
  void login(
      T player,
      String username,
      UUID uniqueId,
      String version,
      ChunkCachingProtocol chunkCachingProtocol,
      ShadowProtocol shadowProtocol,
      List<AddonExtension> addons,
      List<ModificationExtension> modifications,
      List<PackageExtension> packages);

  /**
   * Called when a player is disconnected from the server.
   *
   * @param uniqueId The unique identifier of the disconnected player.
   */
  void disconnect(UUID uniqueId);
}
