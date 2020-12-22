package net.labymod.serverapi.api.protocol.chunkcaching.legacy;

import com.google.inject.assistedinject.Assisted;

public interface ChunkCacheLegacySingle<T> extends ChunkCacheLegacy<T> {

  boolean isGroundUpContinuous();

  /**
   * Factory for {@link ChunkCacheLegacySingle}.
   *
   * @param <T> The packet type.
   */
  interface Factory<T> {

    ChunkCacheLegacySingle<T> create(
        @Assisted("hash") int hash,
        @Assisted("x") int x,
        @Assisted("z") int z,
        @Assisted Object map,
        @Assisted boolean groundUpContinuous);
  }
}
