package net.labymod.serverapi.bukkit.protocol.chunkcaching;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.protocol.chunkcaching.ChunkCache;
import net.labymod.serverapi.api.protocol.chunkcaching.ChunkPosition;
import net.labymod.serverapi.api.protocol.chunkcaching.ChunkPosition.Factory;
import net.labymod.serverapi.api.protocol.chunkcaching.LabyModPlayerChunkCaching;
import org.bukkit.entity.Player;

@Singleton
public class BukkitLabyModPlayerChunkCaching implements LabyModPlayerChunkCaching<Player> {

  private final Set<ChunkPosition> chunkPositions;
  private final Map<ChunkPosition, ChunkCache> coordinates;
  private final ChunkPosition.Factory chunkPositionFactory;

  @Inject
  private BukkitLabyModPlayerChunkCaching(Factory chunkPositionFactory) {
    this.chunkPositionFactory = chunkPositionFactory;
    this.chunkPositions = Sets.newHashSet();
    this.coordinates = Maps.newConcurrentMap();
  }

  @Override
  public List<Integer> getSendingCaches(ChunkCache[] caches) {
    List<Integer> send = new LinkedList<>();

    for (int i = 0; i < caches.length; i++) {
      ChunkCache chunkCache = caches[i];

      if (chunkPositions.remove(chunkCache.getChunkPosition())) {
        send.add(i);
      } else {
        coordinates.putIfAbsent(chunkCache.getChunkPosition(), chunkCache);
      }
    }

    return send;
  }

  @Override
  public boolean shouldHandleSignSending(int chunkX, int chunkZ) {
    chunkX = chunkX >> 4;
    chunkZ = chunkZ >> 4;

    ChunkPosition chunkPosition = this.chunkPositionFactory.create(chunkX, chunkZ);
    ChunkCache chunkCache = this.coordinates.get(chunkPosition);

    // TODO: 21.12.2020 1.8 Check

    return chunkCache != null;
  }

  @Override
  public void request(LabyModPlayer<Player> player, boolean[] mask, int[][] coordinates) {
    int need = 0;
    Multimap<Class<? extends ChunkCache>, ChunkCache> targets = LinkedListMultimap.create();
    for (int i = 0; i < mask.length; i++) {
      ChunkPosition pos = this.chunkPositionFactory.create(coordinates[i][0], coordinates[i][1]);
      ChunkCache cache = this.coordinates.remove(pos);
      if (cache == null) {
        continue;
      }
      // Flush all chunks with given hash and send them!
      if (mask[i]) {
        // flushSigns( proto, player, cache );
        continue; // We do not need to send this chunk to the player, yay! Just saved some traffic
      }
      need++;
      chunkPositions.add(cache.getChunkPosition());

      targets.put(cache.getClass(), cache);
    }

    if (need == 0 || !player.getPlayer().isOnline()) {
      return;
    }

    // This groups by class -> creates one BulkChunkPacket instead of several others
    for (Map.Entry<Class<? extends ChunkCache>, Collection<ChunkCache>> entry :
        targets.asMap().entrySet()) {
      Collection<ChunkCache> v = entry.getValue();
      if (v.isEmpty()) {
        continue;
      }
      v.stream()
          .findAny()
          .ifPresent(
              (cache) -> {
                cache.send(player, v);
              });
      for (ChunkCache cache : v) {
        // flushSigns( proto, player, cache );
      }
    }
  }

  @Override
  public void clear() {
    this.coordinates.clear();
    this.chunkPositions.clear();
  }

  @Override
  public void clearOlder(long timestamp) {

    Iterator<Entry<ChunkPosition, ChunkCache>> iterator = this.coordinates.entrySet().iterator();
    while (iterator.hasNext()) {
      ChunkCache chunkCache = iterator.next().getValue();

      if (chunkCache.getStoredTimestamp() < timestamp) {
        iterator.remove();
        this.coordinates.remove(chunkCache.getChunkPosition());
      }
    }
  }
}
