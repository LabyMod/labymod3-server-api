package net.labymod.serverapi.common.protocol.chunkcaching.modern;

import io.netty.buffer.ByteBuf;
import net.labymod.serverapi.api.protocol.chunkcaching.ChunkPosition;
import net.labymod.serverapi.api.protocol.chunkcaching.modern.ChunkCacheModern;
import net.labymod.serverapi.common.protocol.chunkcaching.DefaultChunkCache;

public abstract class DefaultChunkCacheModern extends DefaultChunkCache
    implements ChunkCacheModern {

  private final ByteBuf buffer;

  public DefaultChunkCacheModern(
      int hash, int x, int z, ByteBuf buffer, ChunkPosition.Factory chunkPositionFactory) {
    super(hash, x, z, chunkPositionFactory);
    this.buffer = buffer;
  }

  /** {@inheritDoc} */
  @Override
  public ByteBuf getBuffer() {
    return this.buffer;
  }
}
