package net.labymod.serverapi.api.protocol.chunkcaching.legacy;

import java.util.List;
import net.labymod.serverapi.api.protocol.chunkcaching.ChunkCache;

/**
 * Represents a chunk cache for legacy versions.
 *
 * @param <T> The type of the packet.
 */
public interface ChunkCacheLegacy<T> extends ChunkCache {

  /**
   * Retrieves the Minecraft Map object.
   *
   * @return The minecraft Map object.
   */
  Object getMap();

  /**
   * Retrieves a collection with all sign updates.
   *
   * @return A collection with all sign updates.
   */
  List<T> getSignUpdates();
}
