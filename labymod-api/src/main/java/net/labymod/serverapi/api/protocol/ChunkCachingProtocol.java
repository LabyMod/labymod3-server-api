package net.labymod.serverapi.api.protocol;

/** Represents the chunk caching protocol. */
public interface ChunkCachingProtocol extends Protocol {

  /** A factory for creating {@link ChunkCachingProtocol}'s. */
  interface Factory {

    /**
     * Creates a new {@link ChunkCachingProtocol} with the {@code version} and whether it is {@code
     * enabled}.
     *
     * @param version The version of the chunk caching protocol.
     * @param enabled {@code true} if the chunk caching protocol enabled, otherwise {@code false}.
     * @return A created chunk caching protocol.
     */
    ChunkCachingProtocol create(int version, boolean enabled);
  }
}
