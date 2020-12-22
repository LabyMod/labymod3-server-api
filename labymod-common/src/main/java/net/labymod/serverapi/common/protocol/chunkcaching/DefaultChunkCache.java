package net.labymod.serverapi.common.protocol.chunkcaching;

import net.labymod.serverapi.api.protocol.chunkcaching.ChunkCache;
import net.labymod.serverapi.api.protocol.chunkcaching.ChunkPosition;

public abstract class DefaultChunkCache implements ChunkCache {

  private final long storedTimestamp;
  private final int hash;
  private final ChunkPosition chunkPosition;

  public DefaultChunkCache(
      int hash,
      int x,
      int z,
      ChunkPosition.Factory chunkPositionFactory) {
    this.storedTimestamp = System.currentTimeMillis();
    this.hash = hash;
    this.chunkPosition = chunkPositionFactory.create(x, z);
  }

  /** {@inheritDoc} */
  @Override
  public long getStoredTimestamp() {
    return this.storedTimestamp;
  }

  /** {@inheritDoc} */
  @Override
  public int getHash() {
    return this.hash;
  }

  /** {@inheritDoc} */
  @Override
  public int getX() {
    return this.chunkPosition.getX();
  }

  /** {@inheritDoc} */
  @Override
  public int getZ() {
    return this.chunkPosition.getZ();
  }

  /** {@inheritDoc} */
  @Override
  public ChunkPosition getChunkPosition() {
    return this.chunkPosition;
  }
}
