package net.labymod.serverapi.api.protocol.chunkcaching.legacy;

import com.google.inject.assistedinject.Assisted;

/**
 * Represents a single chunk cache for legacy version.
 *
 * @param <T> The type of the packet.
 */
public interface ChunkCacheLegacySingle<T> extends ChunkCacheLegacy<T> {

  /**
   * Whether the chunk is ground up continuous.
   *
   * @return {@code true} if the chunk is ground up continuous, otherwise {@code false}.
   */
  boolean isGroundUpContinuous();

  /**
   * Factory for {@link ChunkCacheLegacySingle}.
   *
   * @param <T> The packet type.
   */
  interface Factory<T> {

    /**
     * Creates a new {@link ChunkCacheLegacySingle} with the given parameters.
     *
     * @param hash The hash of the chunk.
     * @param x The x position of the chunk.
     * @param z The z position of the chunk.
     * @param map The non-null Minecraft map.
     * @param groundUpContinuous {@code true} if ground up continuous, otherwise {@code false}.
     * @return A created chunk cache legacy single.
     */
    ChunkCacheLegacySingle<T> create(
        @Assisted("hash") int hash,
        @Assisted("x") int x,
        @Assisted("z") int z,
        @Assisted Object map,
        @Assisted boolean groundUpContinuous);
  }
}
