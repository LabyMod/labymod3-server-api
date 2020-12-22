package net.labymod.serverapi.api.protocol.chunkcaching;

import com.google.inject.assistedinject.Assisted;

/** Represents the position of a chunk. */
public interface ChunkPosition {

  /**
   * Retrieves the x coordinate of the chunk position.
   *
   * @return The x coordinate of the chunk position.
   */
  int getX();

  /**
   * Retrieves the z coordinate of the chunk position.
   *
   * @return The z coordinate of the chunk position.
   */
  int getZ();

  /** Factory for {@link ChunkPosition}. */
  interface Factory {

    /**
     * Creates a new {@link ChunkPosition} with the given {@code x} and {@code z}.
     *
     * @param x The x position of the chunk.
     * @param z The z position of the chunk.
     * @return A created chunk position
     */
    ChunkPosition create(@Assisted("x") int x, @Assisted("z") int z);
  }
}
