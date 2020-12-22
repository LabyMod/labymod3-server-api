package net.labymod.serverapi.api.protocol.chunkcaching;

import java.util.List;
import net.labymod.serverapi.api.player.LabyModPlayer;

public interface LabyModPlayerChunkCaching<T> {

  List<Integer> getSendingCaches(ChunkCache[] caches);

  boolean shouldHandleSignSending(int chunkX, int chunkZ);

  void request(LabyModPlayer<T> player, boolean[] mask, int[][] coordinates);

  void clear();

  void clearOlder(long timestamp);
}
