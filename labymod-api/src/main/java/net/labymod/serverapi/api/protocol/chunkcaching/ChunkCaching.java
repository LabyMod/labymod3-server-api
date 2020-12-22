package net.labymod.serverapi.api.protocol.chunkcaching;

import java.util.Map;
import java.util.UUID;

public interface ChunkCaching<T, P> {

  LabyModPlayerChunkCaching<T, P> getChunkCache(UUID uniqueId);

  Map<UUID, LabyModPlayerChunkCaching<T, P>> getCache();


}
