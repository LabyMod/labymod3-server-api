package net.labymod.serverapi.api.protocol;

/** Represents the shadow protocol. */
public interface ShadowProtocol extends Protocol {

  /** A factory for creating {@link ShadowProtocol}'s. */
  interface Factory {

    /**
     * Creates a new {@link ShadowProtocol} with the {@code version} and whether it is {@code
     * enabled}.
     *
     * @param version The version of the shadow protocol.
     * @param enabled {@code true} if the shadow protocol enabled, otherwise {@code false}.
     * @return A created shadow protocol.
     */
    ShadowProtocol create(int version, boolean enabled);
  }
}
