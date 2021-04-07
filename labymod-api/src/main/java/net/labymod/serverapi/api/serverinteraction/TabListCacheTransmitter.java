package net.labymod.serverapi.api.serverinteraction;

import java.util.UUID;

/**
 * Represents the tab list cache packet.
 *
 * <p><b>Overview:</b>
 *
 * <p>LabyMod has a cache for the player tab-list. If you want to spawn a NPC, you have to add it to
 * the tab-list to make it visible and remove it again to keep the tab-list clean. With LabyMod you
 * can add the NPC to the tab-list and remove it in the same tick again. The client will remember
 * the information and use it when you spawn the actual Player entity.
 */
public interface TabListCacheTransmitter {

  /**
   * Whether the tab list cache should be enabled or not.
   *
   * @return {@code true} if the tab list cache is enabled, otherwise {@code false}.
   */
  boolean isEnabled();

  /**
   * Changes whether the tab list cache should be enabled or not.
   *
   * @param enabled {@code true} if the tab list cache should be enabled, otherwise {@code false}.
   */
  void setEnabled(boolean enabled);

  /**
   * Transmits the tab list cache packet to the laby client.
   *
   * @param uniqueId The unique identifier of the laby client user.
   */
  void transmit(UUID uniqueId);

  /** Transmits the tab list cache packet to all online laby users. */
  void transmitBroadcast();
}
