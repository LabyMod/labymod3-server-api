package net.labymod.serverapi.common.serverinteraction;

import com.google.gson.JsonObject;
import java.util.UUID;
import net.labymod.serverapi.api.LabyService;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.api.serverinteraction.WatermarkTransmitter;

public class DefaultWatermarkTransmitter implements WatermarkTransmitter {

  private static final String WATERMARK_CHANNEL = "watermark";
  private final LabyModPlayerService<?> playerService;
  private final PayloadCommunicator payloadCommunicator;

  public DefaultWatermarkTransmitter(LabyService service) {
    this.payloadCommunicator = service.getPayloadCommunicator();
    this.playerService = service.getLabyPlayerService();
  }

  /** {@inheritDoc} */
  @Override
  public void transmit(UUID uniqueId, boolean showWatermark) {
    JsonObject watermarkObject = new JsonObject();
    watermarkObject.addProperty("visible", showWatermark);

    this.payloadCommunicator.sendLabyModMessage(uniqueId, WATERMARK_CHANNEL, watermarkObject);
  }

  /** {@inheritDoc} */
  @Override
  public void broadcastTransmit(boolean showWatermark) {
    for (LabyModPlayer<?> player : this.playerService.getPlayers()) {
      this.transmit(player.getUniqueId(), showWatermark);
    }
  }
}
