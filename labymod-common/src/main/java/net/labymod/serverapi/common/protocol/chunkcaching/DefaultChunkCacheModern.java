package net.labymod.serverapi.common.protocol.chunkcaching;

import io.netty.buffer.ByteBuf;
import net.labymod.serverapi.api.protocol.chunkcaching.ChunkCacheModern;
import net.labymod.serverapi.api.protocol.chunkcaching.ChunkPosition;

public abstract class DefaultChunkCacheModern extends DefaultChunkCache
    implements ChunkCacheModern {

  private final ByteBuf buffer;

  public DefaultChunkCacheModern(
      int hash, int x, int z, ByteBuf buffer, ChunkPosition.Factory chunkPositionFactory) {
    super(hash, x, z, chunkPositionFactory);
    this.buffer = buffer;
  }

  @Override
  public ByteBuf getBuffer() {
    return this.buffer;
  }
}
