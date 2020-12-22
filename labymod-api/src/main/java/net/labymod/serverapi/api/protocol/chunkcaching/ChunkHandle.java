package net.labymod.serverapi.api.protocol.chunkcaching;

import net.labymod.serverapi.api.player.LabyModPlayer;

public interface ChunkHandle<L, P, T> {

  boolean handle(
      LabyModPlayer<L> player, LabyModPlayerChunkCaching<L, P> chunkCaching, T packetType, P packet);
}
