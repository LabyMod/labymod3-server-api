package net.labymod.serverapi.bukkit.protocol.chunkcaching;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.inject.Singleton;
import java.lang.reflect.InvocationTargetException;
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
import net.labymod.serverapi.api.protocol.chunkcaching.legacy.ChunkCacheLegacy;
import net.labymod.serverapi.common.guice.LabyModInjector;
import org.bukkit.entity.Player;

@Singleton
public class BukkitLabyModPlayerChunkCaching
    implements LabyModPlayerChunkCaching<Player, PacketContainer> {

  private final Set<ChunkPosition> chunkPositions;
  private final Map<ChunkPosition, ChunkCache> coordinates;
  private final ChunkPosition.Factory chunkPositionFactory;

  public BukkitLabyModPlayerChunkCaching() {
    this.chunkPositionFactory = LabyModInjector.getInstance().getInjectedInstance(Factory.class);
    this.chunkPositions = Sets.newHashSet();
    this.coordinates = Maps.newConcurrentMap();
  }

  /** {@inheritDoc} */
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

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public boolean shouldHandleSignSending(PacketContainer packet) {
    BlockPosition blockPosition = packet.getBlockPositionModifier().read(0);
    int chunkX = blockPosition.getX() >> 4;
    int chunkZ = blockPosition.getZ() >> 4;

    ChunkPosition chunkPosition = this.chunkPositionFactory.create(chunkX, chunkZ);
    ChunkCache chunkCache = this.coordinates.get(chunkPosition);

    if (chunkCache instanceof ChunkCacheLegacy) {
      ((ChunkCacheLegacy<PacketContainer>) chunkCache).getSignUpdates().add(packet);
    }

    return chunkCache != null;
  }

  /** {@inheritDoc} */
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

      if (mask[i]) {
        this.flushSigns(player, cache);
        continue;
      }
      need++;
      chunkPositions.add(cache.getChunkPosition());

      targets.put(cache.getClass(), cache);
    }

    if (need == 0 || !player.getPlayer().isOnline()) {
      return;
    }

    for (Map.Entry<Class<? extends ChunkCache>, Collection<ChunkCache>> entry :
        targets.asMap().entrySet()) {
      Collection<ChunkCache> value = entry.getValue();

      if (value.isEmpty()) {
        continue;
      }

      value.stream()
          .findAny()
          .ifPresent(
              (cache) -> {
                cache.send(player, value);
              });
      value.forEach(cache -> flushSigns(player, cache));
    }
  }

  /** {@inheritDoc} */
  @Override
  public void clear() {
    this.coordinates.clear();
    this.chunkPositions.clear();
  }

  /** {@inheritDoc} */
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

  @SuppressWarnings("unchecked")
  private void flushSigns(LabyModPlayer<Player> player, ChunkCache chunkCache) {
    if (chunkCache instanceof ChunkCacheLegacy) {
      ChunkCacheLegacy<PacketContainer> chunkCacheLegacy =
          (ChunkCacheLegacy<PacketContainer>) chunkCache;

      for (PacketContainer singUpdate : chunkCacheLegacy.getSignUpdates()) {
        try {
          ProtocolLibrary.getProtocolManager().sendServerPacket(player.getPlayer(), singUpdate);
        } catch (InvocationTargetException exception) {
          exception.printStackTrace();
        }
      }
    }
  }
}
