package net.labymod.serverapi.api.protocol.chunkcaching;

import com.google.inject.assistedinject.Assisted;
import java.util.Collection;
import net.labymod.serverapi.api.player.LabyModPlayer;

public interface ChunkCache {

  /**
   * Retrieves the timestamp when the cache was stored.
   *
   * @return The timestamp when the cache was stored.
   */
  long getStoredTimestamp();

  /**
   * Retrieves the hash of the chunk cache.
   *
   * @return The chunk cache hash.
   */
  int getHash();

  /**
   * Retrieves the x position of the chunk.
   *
   * @return The x position of the chunk.
   */
  int getX();

  /**
   * Retrieves the y position of the chunk.
   *
   * @return The z position of the chunk.
   */
  int getZ();

  /**
   * Retrieves the position of the chunk.
   *
   * @return The chunk position.
   */
  ChunkPosition getChunkPosition();

  /**
   * Sends a packet to the given {@code player}.
   *
   * @param player The player who is to receive the packet.
   * @param chunkCaches A collection with all cached chunks.
   * @param <T> The type of the player.
   */
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
