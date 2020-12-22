package net.labymod.serverapi.api.protocol.chunkcaching;

import com.google.inject.assistedinject.Assisted;
import io.netty.buffer.ByteBuf;

public interface ChunkCacheModern extends ChunkCache {

  ByteBuf getBuffer();

  interface Factory {

    ChunkCacheModern create(
        @Assisted("hash") int hash,
        @Assisted("x") int x,
        @Assisted("z") int z,
        @Assisted ByteBuf byteBuf);
  }
}
