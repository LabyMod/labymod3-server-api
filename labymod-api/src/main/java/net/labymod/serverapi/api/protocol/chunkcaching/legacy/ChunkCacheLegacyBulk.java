package net.labymod.serverapi.api.protocol.chunkcaching.legacy;

import com.google.inject.assistedinject.Assisted;

/**
 * Represents a bulk chunk cache.
 *
 * @param <T> The type of the packet.
 */
public interface ChunkCacheLegacyBulk<T> extends ChunkCacheLegacy<T> {

  /**
   * Whether the bulk chunk cache is skylight.
   *
   * @return {@code true} if the bulk chunk cache is skylight, otherwise {@code false}.
   */
  boolean isSkylight();

  /**
   * Factory for {@link ChunkCacheLegacyBulk}.
   *
   * @param <T> The type of the Packet.
   */
  interface Factory<T> {
    ChunkCacheLegacyBulk<T> create(
        @Assisted("hash") int hash,
        @Assisted("x") int x,
        @Assisted("z") int z,
        @Assisted Object map,
        @Assisted boolean skylight);
  }
}
