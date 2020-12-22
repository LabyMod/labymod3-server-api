package net.labymod.serverapi.api.protocol.chunkcaching;

import com.google.inject.assistedinject.Assisted;

public interface ChunkPosition {

  int getX();

  int getZ();

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
