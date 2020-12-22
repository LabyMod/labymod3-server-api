package net.labymod.serverapi.bukkit.protocol.chunkcaching;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.google.inject.Inject;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.List;
import net.jpountz.xxhash.XXHashFactory;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.protocol.chunkcaching.ChunkHandle;
import net.labymod.serverapi.api.protocol.chunkcaching.LabyModPlayerChunkCaching;
import net.labymod.serverapi.api.protocol.chunkcaching.legacy.ChunkCacheLegacy;
import net.labymod.serverapi.api.protocol.chunkcaching.legacy.ChunkCacheLegacyBulk;
import net.labymod.serverapi.api.protocol.chunkcaching.legacy.ChunkCacheLegacySingle;
import net.labymod.serverapi.api.protocol.chunkcaching.legacy.ChunkCacheLegacySingle.Factory;
import net.labymod.serverapi.bukkit.util.NetworkHelper;
import org.bukkit.entity.Player;

public class BukkitChunkHandleLegacy implements ChunkHandle<Player, PacketContainer, PacketType> {

  private static final int MAGIC_NUMBER = -42421337;

  private final ChunkCacheLegacySingle.Factory<PacketContainer> chunkCacheLegacySingleFactory;
  private final ChunkCacheLegacyBulk.Factory<PacketContainer> chunkCacheLegacyBulkFactory;
  private final PayloadCommunicator payloadCommunicator;

  private final XXHashFactory hashFactory;

  @Inject
  public BukkitChunkHandleLegacy(
      Factory<PacketContainer> chunkCacheLegacySingleFactory,
      ChunkCacheLegacyBulk.Factory<PacketContainer> chunkCacheLegacyBulkFactory,
      PayloadCommunicator payloadCommunicator,
      XXHashFactory hashFactory) {
    this.chunkCacheLegacySingleFactory = chunkCacheLegacySingleFactory;
    this.chunkCacheLegacyBulkFactory = chunkCacheLegacyBulkFactory;
    this.payloadCommunicator = payloadCommunicator;
    this.hashFactory = hashFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean handle(
      LabyModPlayer<Player> player,
      LabyModPlayerChunkCaching<Player, PacketContainer> chunkCaching,
      PacketType packetType,
      PacketContainer packet) {

    if (packetType == Server.MAP_CHUNK) {
      Object chunkMap = packet.getModifier().read(2);
      boolean groundUpContinuous = packet.getBooleans().read(0);

      if (!groundUpContinuous || NetworkHelper.getInstance().getChunkMapB(chunkMap) != 0) {

        ChunkCacheLegacy<?>[] chunkCacheLegacies =
            new ChunkCacheLegacy[] {
              this.chunkCacheLegacySingleFactory.create(
                  this.hashSingle(NetworkHelper.getInstance().getChunkMapA(chunkMap)),
                  packet.getIntegers().read(0),
                  packet.getIntegers().read(1),
                  chunkMap,
                  groundUpContinuous)
            };

        List<Integer> maps = chunkCaching.getSendingCaches(chunkCacheLegacies);

        if (maps == null || maps.isEmpty()) {

          ByteBuffer buffer = ByteBuffer.allocate(16);
          buffer.put((byte) 0); // Single chunk
          buffer.put((byte) (groundUpContinuous ? 1 : 0));
          buffer.putShort((short) 1);
          buffer.putInt(chunkCacheLegacies[0].getHash());
          buffer.putInt(chunkCacheLegacies[0].getX());
          buffer.putInt(chunkCacheLegacies[0].getZ());

          this.payloadCommunicator.sendChunkCachingProtocolMessage(
              player.getUniqueId(), buffer.array());
          return true;
        } else {
          return false;
        }
      }

      return false;
    } else if (packetType == Server.MAP_CHUNK_BULK) {

      int[] chunkX = packet.getIntegerArrays().read(0);
      int[] chunkZ = packet.getIntegerArrays().read(1);

      Object[] chunkMaps = (Object[]) packet.getModifier().read(2);

      if (chunkX.length != chunkZ.length || chunkZ.length != chunkMaps.length) {
        throw new RuntimeException(
            String.format(
                "Different array sizes. %d, %d, %d",
                chunkX.length, chunkZ.length, chunkMaps.length));
      }

      boolean skylight = packet.getBooleans().read(0);
      ChunkCacheLegacy<?>[] chunkCacheLegacies = new ChunkCacheLegacy[chunkMaps.length];

      for (int i = 0; i < chunkCacheLegacies.length; i++) {
        byte[] data = NetworkHelper.getInstance().getChunkMapA(chunkMaps[i]);
        int hash = hashSingle(data);
        chunkCacheLegacies[i] =
            this.chunkCacheLegacyBulkFactory.create(
                hash, chunkX[i], chunkZ[i], chunkMaps[i], skylight);
      }

      List<Integer> maps = chunkCaching.getSendingCaches(chunkCacheLegacies);

      if (maps.size() == chunkMaps.length) {
        return false;
      }

      // Byte Byte Short (3 * Int per Chunk)
      ByteBuffer buffer = ByteBuffer.allocate(4 + ((chunkCacheLegacies.length - maps.size()) * 12));

      buffer.put((byte) 1); // Bulk Chunk
      buffer.put((byte) (skylight ? 1 : 0));
      buffer.putShort((short) (chunkCacheLegacies.length - maps.size()));

      if (maps.isEmpty()) {

        for (ChunkCacheLegacy<?> chunkCacheLegacy : chunkCacheLegacies) {
          buffer.putInt(chunkCacheLegacy.getHash());
          buffer.putInt(chunkCacheLegacy.getX());
          buffer.putInt(chunkCacheLegacy.getZ());
        }

        this.payloadCommunicator.sendChunkCachingProtocolMessage(
            player.getUniqueId(), buffer.array());
        return true;
      } else {

        int[] newChunkX = new int[maps.size()];
        int[] newChunkZ = new int[maps.size()];
        Object[] output =
            (Object[])
                Array.newInstance(NetworkHelper.getInstance().getChunkMapClass(), maps.size());

        int index = 0;
        for (Integer pos : maps) {
          newChunkX[index] = chunkX[pos];
          newChunkZ[index] = chunkZ[pos];
          output[index] = chunkMaps[pos];
          chunkCacheLegacies[pos] = null;

          index++;
        }
        for (int l = 0; l < chunkCacheLegacies.length; l++) {
          if (chunkCacheLegacies[l] == null) {
            continue;
          }

          buffer.putInt(chunkCacheLegacies[l].getHash());
          buffer.putInt(chunkX[l]);
          buffer.putInt(chunkZ[l]);
        }

        packet.getIntegerArrays().write(0, newChunkX);
        packet.getIntegerArrays().write(1, newChunkZ);
        packet.getModifier().write(2, output);
        this.payloadCommunicator.sendChunkCachingProtocolMessage(
            player.getUniqueId(), buffer.array());
        return false;
      }

    } else if (packetType == Server.UPDATE_SIGN) {
      return chunkCaching.shouldHandleSignSending(packet);
    } else {
      return false;
    }
  }

  private int hashSingle(byte[] data) {
    return hashFactory.hash32().hash(data, 0, data.length, MAGIC_NUMBER);
  }
}
