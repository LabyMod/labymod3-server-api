package net.labymod.serverapi.api.protocol.chunkcaching.modern;

import com.google.inject.assistedinject.Assisted;
import io.netty.buffer.ByteBuf;
import net.labymod.serverapi.api.protocol.chunkcaching.ChunkCache;

/**
 * Represents the chunk cache for modern version.
 *
 * <p>Starts from the 1.12.x
 */
public interface ChunkCacheModern extends ChunkCache {

  ByteBuf getBuffer();

  /** Factory for the {@link ChunkCacheModern}. */
  interface Factory {

    ChunkCacheModern create(
        @Assisted("hash") int hash,
        @Assisted("x") int x,
        @Assisted("z") int z,
        @Assisted ByteBuf byteBuf);
  }
}
