package net.labymod.serverapi.bukkit.protocol.chunkcaching.legacy;

import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.protocol.chunkcaching.ChunkCache;
import net.labymod.serverapi.api.protocol.chunkcaching.ChunkPosition;
import net.labymod.serverapi.api.protocol.chunkcaching.legacy.ChunkCacheLegacy;
import net.labymod.serverapi.bukkit.util.NetworkHelper;
import net.labymod.serverapi.common.protocol.chunkcaching.legacy.DefaultChunkCacheLegacyBulk;
import org.bukkit.entity.Player;

public class BukkitChunkCacheLegacyBulk extends DefaultChunkCacheLegacyBulk<PacketContainer> {

  @AssistedInject
  private BukkitChunkCacheLegacyBulk(
      @Assisted("hash") int hash,
      @Assisted("x") int x,
      @Assisted("z") int z,
      @Assisted Object map,
      @Assisted boolean skylight,
      ChunkPosition.Factory chunkPositionFactory) {
    super(hash, x, z, map, skylight, chunkPositionFactory);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> void send(LabyModPlayer<T> player, Collection<ChunkCache> chunkCaches) {
    PacketContainer packet = new PacketContainer(Server.MAP_CHUNK_BULK);
    packet
        .getModifier()
        .write(
            4,
            NetworkHelper.getInstance().getWorldHandle(((Player) player.getPlayer()).getWorld()));

    int chunkCacheSize = chunkCaches.size();

    int[] chunkX = new int[chunkCacheSize];
    int[] chunkZ = new int[chunkCacheSize];

    Object[] maps =
        (Object[])
            Array.newInstance(NetworkHelper.getInstance().getChunkMapClass(), chunkCacheSize);
    int index = 0;
    for (ChunkCache chunkCache : chunkCaches) {
      chunkX[index] = chunkCache.getX();
      chunkZ[index] = chunkCache.getZ();
      maps[index] = ((ChunkCacheLegacy<PacketContainer>) chunkCache).getMap();

      index++;
    }

    packet.getIntegerArrays().write(0, chunkX);
    packet.getIntegerArrays().write(1, chunkZ);

    packet.getModifier().write(2, maps);
    packet.getBooleans().write(0, this.isSkylight());

    try {
      ProtocolLibrary.getProtocolManager().sendServerPacket((Player) player.getPlayer(), packet);
    } catch (InvocationTargetException exception) {
      exception.printStackTrace();
    }
  }
}
