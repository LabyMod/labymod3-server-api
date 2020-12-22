package net.labymod.serverapi.bukkit.protocol.chunkcaching.legacy;

import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.protocol.chunkcaching.ChunkCache;
import net.labymod.serverapi.api.protocol.chunkcaching.ChunkPosition;
import net.labymod.serverapi.common.protocol.chunkcaching.legacy.DefaultChunkCacheLegacySingle;
import org.bukkit.entity.Player;

public class BukkitChunkCacheLegacySingle extends DefaultChunkCacheLegacySingle<PacketContainer> {

  @AssistedInject
  public BukkitChunkCacheLegacySingle(
      @Assisted("hash") int hash,
      @Assisted("x") int x,
      @Assisted("z") int z,
      @Assisted Object map,
      @Assisted boolean groundUpContinuous,
      ChunkPosition.Factory chunkPositionFactory) {
    super(hash, x, z, map, groundUpContinuous, chunkPositionFactory);
  }

  @Override
  public <T> void send(LabyModPlayer<T> player, Collection<ChunkCache> chunkCaches) {

    for (ChunkCache chunkCache : chunkCaches) {
      BukkitChunkCacheLegacySingle chunkCacheLegacySingle =
          (BukkitChunkCacheLegacySingle) chunkCache;

      PacketContainer packetContainer = new PacketContainer(Server.MAP_CHUNK);
      packetContainer.getIntegers().write(0, chunkCacheLegacySingle.getX());
      packetContainer.getIntegers().write(1, chunkCacheLegacySingle.getZ());
      packetContainer.getModifier().write(2, chunkCacheLegacySingle.getMap());
      packetContainer.getBooleans().write(0, chunkCacheLegacySingle.isGroundUpContinuous());

      try {
        ProtocolLibrary.getProtocolManager()
            .sendServerPacket((Player) player.getPlayer(), packetContainer);
      } catch (InvocationTargetException exception) {
        exception.printStackTrace();
      }
    }
  }
}
