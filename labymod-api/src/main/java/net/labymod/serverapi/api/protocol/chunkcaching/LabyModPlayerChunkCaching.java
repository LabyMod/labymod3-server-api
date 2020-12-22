package net.labymod.serverapi.api.protocol.chunkcaching;

import java.util.List;
import net.labymod.serverapi.api.player.LabyModPlayer;

public interface LabyModPlayerChunkCaching<T, P> {

  List<Integer> getSendingCaches(ChunkCache[] caches);

  boolean shouldHandleSignSending(P packet);

  void request(LabyModPlayer<T> player, boolean[] mask, int[][] coordinates);

  void clear();

  void clearOlder(long timestamp);
}
