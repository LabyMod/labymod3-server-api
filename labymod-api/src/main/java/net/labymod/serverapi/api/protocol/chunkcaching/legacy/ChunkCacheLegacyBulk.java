package net.labymod.serverapi.api.protocol.chunkcaching.legacy;

import com.google.inject.assistedinject.Assisted;

public interface ChunkCacheLegacyBulk<T> extends ChunkCacheLegacy<T> {

  boolean isSkylight();

  interface Factory<T> {
    ChunkCacheLegacyBulk<T> create(
        @Assisted("hash") int hash,
        @Assisted("x") int x,
        @Assisted("z") int z,
        @Assisted Object map,
        @Assisted boolean skylight);
  }
}
