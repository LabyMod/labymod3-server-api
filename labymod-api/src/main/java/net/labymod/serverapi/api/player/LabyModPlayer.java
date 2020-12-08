package net.labymod.serverapi.api.player;

import java.util.List;
import java.util.UUID;
import net.labymod.serverapi.api.extension.AddonExtension;
import net.labymod.serverapi.api.extension.ModificationExtension;
import net.labymod.serverapi.api.extension.PackageExtension;
import net.labymod.serverapi.api.protocol.ChunkCachingProtocol;
import net.labymod.serverapi.api.protocol.ShadowProtocol;

/**
 * Represents a player that uses LabyMod.
 *
 * @param <T> The player type of the implemented software.
 */
public interface LabyModPlayer<T> {

  /**
   * Retrieves the player of the implemented software.
   *
   * @return The player of the implemented software.
   */
  T getPlayer();

  /**
   * Retrieves the username of this LabyMod player.
   *
   * @return The player's username.
   */
  String getUsername();

  /**
   * Retrieves the unique identifier of this LabyMod player.
   *
   * @return The player's unique identifier.
   */
  UUID getUniqueId();

  /**
   * Retrieves the current version of this LabyMod player.
   *
   * @return The client version of this LabyMod player.
   */
  String getVersion();

  /**
   * Retrieves the chunk caching protocol of this player.
   *
   * @return The players chunk caching protocol.
   */
  ChunkCachingProtocol getChunkCachingProtocol();

  /**
   * Retrieves the shadow protocol of this player.
   *
   * @return The players shadow protocol.
   */
  ShadowProtocol getShadowProtocol();

  /**
   * Retrieves a collection with all installed addons of this player.
   *
   * @return A collection with all installed addons.
   */
  List<AddonExtension> getAddons();

  /**
   * Retrieves a collection with all installed modifications of this player.
   *
   * @return A collection with all installed modifications.
   */
  List<ModificationExtension> getModifications();

  /**
   * Retrieves a collection with all installed packages of this player.
   *
   * @return A collection with all installed packages.
   */
  List<PackageExtension> getPackages();

  /**
   * A factory for creating {@link LabyModPlayer}'s.
   *
   * @param <T> The player type of the implemented software.
   */
  interface Factory<T> {

    /**
     * Creates a new {@link LabyModPlayer} with the given parameters.
     *
     * @param player The player.
     * @param username The player's username.
     * @param uniqueId The player's unique identifier.
     * @param version The version of the LabyMod player.
     * @param chunkCachingProtocol The chunk caching protocol of the player.
     * @param shadowProtocol The shadow protocol of the player.
     * @param addons A collection with all installed addons.
     * @param modifications A collection with all installed modifications.
     * @return A created LabyMod player.
     */
    LabyModPlayer<T> create(
        T player,
        String username,
        UUID uniqueId,
        String version,
        ChunkCachingProtocol chunkCachingProtocol,
        ShadowProtocol shadowProtocol,
        List<AddonExtension> addons,
        List<ModificationExtension> modifications);

    /**
     * Creates a new {@link LabyModPlayer} with the given parameters.
     *
     * @param player The player.
     * @param username The player's username.
     * @param uniqueId The player's unique identifier.
     * @param version The version of the LabyMod player.
     * @param chunkCachingProtocol The chunk caching protocol of the player.
     * @param shadowProtocol The shadow protocol of the player.
     * @param addons A collection with all installed addons.
     * @param modifications A collection with all installed modifications.
     * @param packages A collection with all installed packages.
     * @return A created LabyMod player.
     */
    LabyModPlayer<T> create(
        T player,
        String username,
        UUID uniqueId,
        String version,
        ChunkCachingProtocol chunkCachingProtocol,
        ShadowProtocol shadowProtocol,
        List<AddonExtension> addons,
        List<ModificationExtension> modifications,
        List<PackageExtension> packages);
  }
}
