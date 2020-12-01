package net.labymod.serverapi.api.serverinteraction;

import java.util.UUID;

/**
 * Represents the cine scope packet.
 *
 * <p><b>Overview:</b>
 *
 * <p>It is possible to use Cine Scopes (Black bars) for game mode cinematics.
 */
public interface CineScopes {

  /**
   * Sends the cine scope packet to the client.
   *
   * @param uniqueId The unique identifier of the receiver.
   * @param showCineScopes {@code true} if the black bar should be fade in, for fade out{@code
   *     false}.
   * @param coveragePercent The height of the black bars in percent. The minimum of the black bars
   *     is 1% and the maximum is 50%.
   * @param duration The duration
   */
  void sendCineScope(UUID uniqueId, boolean showCineScopes, int coveragePercent, long duration);
}
