package net.labymod.serverapi.common.protocol;

import net.labymod.serverapi.api.protocol.ChunkCachingProtocol;

public class DefaultChunkCachingProtocolFactory implements ChunkCachingProtocol.Factory {

  /** {@inheritDoc} */
  @Override
  public ChunkCachingProtocol create(int version, boolean enabled) {
    return new DefaultChunkCachingProtocol(version, enabled);
  }
}
