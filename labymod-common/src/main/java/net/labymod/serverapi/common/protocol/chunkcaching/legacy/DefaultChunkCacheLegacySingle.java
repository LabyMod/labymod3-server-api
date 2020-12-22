package net.labymod.serverapi.common.protocol.chunkcaching.legacy;

import net.labymod.serverapi.api.protocol.chunkcaching.ChunkPosition;
import net.labymod.serverapi.api.protocol.chunkcaching.legacy.ChunkCacheLegacySingle;

public abstract class DefaultChunkCacheLegacySingle<T> extends DefaultChunkCacheLegacy<T>
    implements ChunkCacheLegacySingle<T> {

  private final boolean groundUpContinuous;

  public DefaultChunkCacheLegacySingle(
      int hash,
      int x,
      int z,
      Object map,
      boolean groundUpContinuous,
      ChunkPosition.Factory chunkPositionFactory) {
    super(hash, x, z, map, chunkPositionFactory);
    this.groundUpContinuous = groundUpContinuous;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isGroundUpContinuous() {
    return this.groundUpContinuous;
  }
}
