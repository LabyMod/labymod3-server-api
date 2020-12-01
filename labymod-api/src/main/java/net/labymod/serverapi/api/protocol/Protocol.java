package net.labymod.serverapi.api.protocol;

/** Represents a LabyMod protocol. */
public interface Protocol {

  /**
   * Retrieves the version of this protocol.
   *
   * @return The version of this protocol.
   */
  int getVersion();

  /**
   * Whether the protocol is enabled.
   *
   * @return {@code true} if the protocol is enabled, otherwise {@code false.}
   */
  boolean isEnabled();
}
