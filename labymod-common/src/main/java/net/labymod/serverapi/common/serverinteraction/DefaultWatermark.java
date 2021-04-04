package net.labymod.serverapi.common.serverinteraction;

import com.google.gson.JsonObject;
import java.util.UUID;
import net.labymod.serverapi.api.LabyService;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.api.serverinteraction.Watermark;

public class DefaultWatermark implements Watermark {

  private static final String WATERMARK_CHANNEL = "watermark";
  private final LabyModPlayerService<?> playerService;
  private final PayloadCommunicator payloadCommunicator;

  public DefaultWatermark(LabyService service) {
    this.payloadCommunicator = service.getPayloadCommunicator();
    this.playerService = service.getLabyPlayerService();
  }

  /** {@inheritDoc} */
  @Override
  public void displayWatermark(UUID uniqueId, boolean showWatermark) {
    JsonObject watermarkObject = new JsonObject();
    watermarkObject.addProperty("visible", showWatermark);

    this.payloadCommunicator.sendLabyModMessage(uniqueId, WATERMARK_CHANNEL, watermarkObject);
  }

  /** {@inheritDoc} */
  @Override
  public void broadcastDisplayWatermark(boolean showWatermark) {
    for (LabyModPlayer<?> player : this.playerService.getPlayers()) {
      this.displayWatermark(player.getUniqueId(), showWatermark);
    }
  }
}
