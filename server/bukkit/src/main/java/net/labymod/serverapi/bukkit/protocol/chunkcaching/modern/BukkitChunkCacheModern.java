package net.labymod.serverapi.bukkit.protocol.chunkcaching.modern;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import java.util.Collection;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.protocol.chunkcaching.ChunkCache;
import net.labymod.serverapi.api.protocol.chunkcaching.ChunkPosition;
import net.labymod.serverapi.bukkit.util.NetworkHelper;
import net.labymod.serverapi.common.protocol.chunkcaching.modern.DefaultChunkCacheModern;
import org.bukkit.entity.Player;

public class BukkitChunkCacheModern extends DefaultChunkCacheModern {

  @AssistedInject
  public BukkitChunkCacheModern(
      @Assisted("hash") int hash,
      @Assisted("x") int x,
      @Assisted("z") int z,
      @Assisted ByteBuf byteBuf,
      ChunkPosition.Factory chunkPositionFactory) {
    super(hash, x, z, byteBuf, chunkPositionFactory);
  }

  /** {@inheritDoc} */
  @Override
  public <T> void send(LabyModPlayer<T> player, Collection<ChunkCache> chunkCaches) {
    Channel channel = NetworkHelper.getInstance().getChannel((Player) player.getPlayer());
    ChannelHandlerContext channelHandlerContext = channel.pipeline().context("laby_chunks");

    for (ChunkCache chunkCache : chunkCaches) {
      BukkitChunkCacheModern bukkitChunkCacheModern = (BukkitChunkCacheModern) chunkCache;
      channelHandlerContext.write(bukkitChunkCacheModern.getBuffer());
    }
  }
}
