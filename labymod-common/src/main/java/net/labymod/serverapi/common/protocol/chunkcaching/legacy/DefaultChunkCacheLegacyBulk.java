package net.labymod.serverapi.common.protocol.chunkcaching.legacy;

import net.labymod.serverapi.api.protocol.chunkcaching.ChunkPosition;
import net.labymod.serverapi.api.protocol.chunkcaching.legacy.ChunkCacheLegacyBulk;

public abstract class DefaultChunkCacheLegacyBulk<T> extends DefaultChunkCacheLegacy<T>
    implements ChunkCacheLegacyBulk<T> {

  private final boolean skylight;

  public DefaultChunkCacheLegacyBulk(
      int hash,
      int x,
      int z,
      Object map,
      boolean skylight,
      ChunkPosition.Factory chunkPositionFactory) {
    super(hash, x, z, map, chunkPositionFactory);
    this.skylight = skylight;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isSkylight() {
    return this.skylight;
  }
}
