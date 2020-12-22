package net.labymod.serverapi.api.protocol.chunkcaching;

import java.util.Map;
import java.util.UUID;

public interface ChunkCaching<T> {

  LabyModPlayerChunkCaching<T> getChunkCache(UUID uniqueId);

  Map<UUID, LabyModPlayerChunkCaching<T>> getCache();


}
