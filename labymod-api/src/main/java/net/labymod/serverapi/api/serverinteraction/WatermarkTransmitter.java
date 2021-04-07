package net.labymod.serverapi.api.serverinteraction;

import java.util.UUID;

/**
 * Represents the watermark packet.
 *
 * <p><b>Overview:</b>
 *
 * <p>This feature will show a LabyMod banner in the bottom right corner. It was made for LabyMod
 * tournaments. A special feature of this watermark is that the server can force an emote to the
 * player without changing the unique identifier.
 */
public interface WatermarkTransmitter {

  /**
   * Transmits the watermark packet to the client.
   *
   * @param uniqueId The unique identifier of the receiver.
   * @param showWatermark {@code true} if the watermark should be displayed, otherwise {@code
   *     false}.
   */
  void transmit(UUID uniqueId, boolean showWatermark);

  /**
   * Transmits the watermark packet to all online laby users.
   *
   * @param showWatermark {@code true} if the watermark should be displayed, otherwise {@code
   *     false}.
   */
  void broadcastTransmit(boolean showWatermark);
}
