package net.labymod.serverapi.api.protocol.chunkcaching;

import com.google.inject.assistedinject.Assisted;
import java.util.Collection;
import net.labymod.serverapi.api.player.LabyModPlayer;

public interface ChunkCache {

  long getStoredTimestamp();

  int getHash();

  int getX();

  int getZ();

  ChunkPosition getChunkPosition();

  <T> void send(LabyModPlayer<T> player, Collection<ChunkCache> chunkCaches);

  /** Factory for {@link ChunkCache}. */
  interface Factory {

    /**
     * Creates a new {@link ChunkCache} with the {@code hash}, {@code x} and {@code z}.
     *
     * @param hash The hash of the chunk cache.
     * @param x The x position of the chunk.
     * @param z The z position of the chunk.
     * @return A created chunk cache.
     */
    ChunkCache create(@Assisted("hash") int hash, @Assisted("x") int x, @Assisted("z") int z);
  }
}
