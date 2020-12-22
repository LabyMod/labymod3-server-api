package net.labymod.serverapi.api.protocol.chunkcaching.legacy;

import java.util.List;
import net.labymod.serverapi.api.protocol.chunkcaching.ChunkCache;

public interface ChunkCacheLegacy<T> extends ChunkCache {

  Object getMap();

  List<T> getSignUpdates();

}
