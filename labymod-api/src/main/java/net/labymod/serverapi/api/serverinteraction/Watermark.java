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
 *
 * @deprecated Use {@link WatermarkTransmitter}.
 */
@Deprecated
public interface Watermark {

  /**
   * Sends the watermark packet to the client.
   *
   * @param uniqueId The unique identifier of the receiver.
   * @param showWatermark {@code true} if the watermark should be displayed, otherwise {@code
   *     false}.
   * @deprecated Use {@link WatermarkTransmitter#transmit(UUID, boolean)}
   */
  @Deprecated
  void displayWatermark(UUID uniqueId, boolean showWatermark);

  /**
   * Sends the watermark packet to all online laby users.
   *
   * @param showWatermark {@code true} if the watermark should be displayed, otherwise {@code
   *     false}.
   * @deprecated Use {@link WatermarkTransmitter#broadcastTransmit(boolean)}
   */
  @Deprecated
  void broadcastDisplayWatermark(boolean showWatermark);
}
