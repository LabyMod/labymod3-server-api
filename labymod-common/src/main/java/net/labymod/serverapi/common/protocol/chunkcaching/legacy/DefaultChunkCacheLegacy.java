package net.labymod.serverapi.common.protocol.chunkcaching.legacy;

import com.google.common.collect.Lists;
import java.util.List;
import net.labymod.serverapi.api.protocol.chunkcaching.ChunkPosition;
import net.labymod.serverapi.api.protocol.chunkcaching.legacy.ChunkCacheLegacy;
import net.labymod.serverapi.common.protocol.chunkcaching.DefaultChunkCache;

public abstract class DefaultChunkCacheLegacy<T> extends DefaultChunkCache
    implements ChunkCacheLegacy<T> {

  private final Object map;
  private final List<T> signUpdates;

  public DefaultChunkCacheLegacy(
      int hash, int x, int z, Object map, ChunkPosition.Factory chunkPositionFactory) {
    super(hash, x, z, chunkPositionFactory);
    this.map = map;
    this.signUpdates = Lists.newLinkedList();
  }

  /** {@inheritDoc} */
  @Override
  public Object getMap() {
    return this.map;
  }

  /** {@inheritDoc} */
  @Override
  public List<T> getSignUpdates() {
    return this.signUpdates;
  }
}
