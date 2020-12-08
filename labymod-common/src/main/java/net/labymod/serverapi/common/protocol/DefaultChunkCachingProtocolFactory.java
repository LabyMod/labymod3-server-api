package net.labymod.serverapi.common.protocol;

import net.labymod.serverapi.api.protocol.ChunkCachingProtocol;

public class DefaultChunkCachingProtocolFactory implements ChunkCachingProtocol.Factory {

  private static final ChunkCachingProtocol.Factory INSTANCE =
      new DefaultChunkCachingProtocolFactory();

  private DefaultChunkCachingProtocolFactory() {}

  public static ChunkCachingProtocol.Factory getInstance() {
    return INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public ChunkCachingProtocol create(int version, boolean enabled) {
    return new DefaultChunkCachingProtocol(version, enabled);
  }
}
