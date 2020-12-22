package net.labymod.serverapi.bukkit.protocol.chunkcaching;

import com.comphenix.protocol.events.PacketContainer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import java.nio.ByteBuffer;
import java.util.List;
import net.jpountz.xxhash.XXHashFactory;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.protocol.chunkcaching.ChunkCache;
import net.labymod.serverapi.api.protocol.chunkcaching.ChunkPosition;
import net.labymod.serverapi.api.protocol.chunkcaching.LabyModPlayerChunkCaching;
import net.labymod.serverapi.bukkit.protocol.chunkcaching.modern.BukkitChunkCacheModern;
import net.labymod.serverapi.common.guice.LabyModInjector;
import org.bukkit.entity.Player;
import us.myles.ViaVersion.ViaVersionPlugin;
import us.myles.ViaVersion.api.ViaAPI;
import us.myles.ViaVersion.api.protocol.ProtocolVersion;

public class ChunkCacheNewerHandle extends ChannelOutboundHandlerAdapter {

  private static final int MAGIC_NUMBER = -42421337;

  private final PayloadCommunicator payloadCommunicator;
  private final Player player;
  private final LabyModPlayerChunkCaching<Player, PacketContainer> playerState;
  private final ViaAPI<Player> viaAPI;

  public ChunkCacheNewerHandle(
      Player player, LabyModPlayerChunkCaching<Player, PacketContainer> playerState) {
    this.payloadCommunicator =
        LabyModInjector.getInstance().getInjectedInstance(PayloadCommunicator.class);
    this.player = player;

    this.playerState = playerState;
    this.viaAPI = ViaVersionPlugin.getInstance().getApi();
  }

  private int readVarInt(ByteBuf buf) {
    int readingNumber = 0;
    int result = 0;

    byte readableBytes;

    do {
      readableBytes = buf.readByte();
      readingNumber |= (readableBytes & 127) << result++ * 7;
    } while ((readableBytes & 128) == 128);

    return readingNumber;
  }

  /** {@inheritDoc} */
  @Override
  public void write(
      ChannelHandlerContext channelHandlerContext, Object message, ChannelPromise channelPromise) {
    try {
      if (!(message instanceof ByteBuf)) {
        channelHandlerContext.write(message);
      } else {
        ByteBuf buf = (ByteBuf) message;

        if (buf.readableBytes() < 4) {
          channelHandlerContext.write(message);
          return;
        }

        int index = buf.readerIndex();

        int packetId = readVarInt(buf);

        if ((this.viaAPI.getPlayerVersion(player) == ProtocolVersion.v1_12_2.getVersion()
                && packetId != 32)
            || (this.viaAPI.getPlayerVersion(player) == ProtocolVersion.v1_15_2.getVersion()
                && packetId != 34)) {
          buf.readerIndex(index);
          channelHandlerContext.write(buf);
          return;
        }
        int x = buf.readInt();
        int z = buf.readInt();
        int hash =
            LabyModInjector.getInstance()
                .getInjectedInstance(XXHashFactory.class)
                .hash32()
                .hash(buf.nioBuffer(), MAGIC_NUMBER);
        buf.readerIndex(index);

        ChunkCache[] caches =
            new ChunkCache[] {
              new BukkitChunkCacheModern(
                  hash,
                  x,
                  z,
                  buf,
                  LabyModInjector.getInstance().getInjectedInstance(ChunkPosition.Factory.class))
            };
        List<Integer> maps = playerState.getSendingCaches(caches);

        if (maps == null || maps.isEmpty()) {
          ByteBuffer payloadMessage = ByteBuffer.allocate(16); // ByteByteShortIntIntInt
          payloadMessage.putInt(1); // The first 4 bytes are only necessary for 1.8
          payloadMessage.putInt(hash);
          payloadMessage.putInt(x);
          payloadMessage.putInt(z);
          this.payloadCommunicator.sendChunkCachingProtocolMessage(
              this.player.getUniqueId(), payloadMessage.array());
          // In this case, do not call ctx.write -> message discarded for now
        } else {
          channelHandlerContext.write(buf);
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
