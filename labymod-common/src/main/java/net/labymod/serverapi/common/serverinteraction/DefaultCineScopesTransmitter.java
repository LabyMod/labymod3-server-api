package net.labymod.serverapi.common.serverinteraction;

import com.google.gson.JsonObject;
import java.util.UUID;
import net.labymod.serverapi.api.LabyService;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.api.serverinteraction.CineScopesTransmitter;

public class DefaultCineScopesTransmitter implements CineScopesTransmitter {

  private static final String CINESCOPES_CHANNEL = "cinescopes";

  private final LabyModPlayerService<?> playerService;
  private final PayloadCommunicator payloadCommunicator;

  public DefaultCineScopesTransmitter(LabyService service) {
    this.payloadCommunicator = service.getPayloadCommunicator();
    this.playerService = service.getLabyPlayerService();
  }

  /** {@inheritDoc} */
  @Override
  public void transmit(UUID uniqueId, boolean showCineScopes, int coveragePercent, long duration) {
    JsonObject cineScopeObject = new JsonObject();

    if (coveragePercent < 0) {
      coveragePercent = 0;
    } else if (coveragePercent > 50) {
      coveragePercent = 50;
    }

    cineScopeObject.addProperty("visible", showCineScopes);
    cineScopeObject.addProperty("coverage", coveragePercent);
    cineScopeObject.addProperty("duration", duration);

    this.payloadCommunicator.sendLabyModMessage(uniqueId, CINESCOPES_CHANNEL, cineScopeObject);
  }

  /** {@inheritDoc} */
  @Override
  public void broadcastTransmit(boolean showCineScopes, int coveragePercent, long duration) {
    for (LabyModPlayer<?> player : this.playerService.getPlayers()) {
      this.transmit(player.getUniqueId(), showCineScopes, coveragePercent, duration);
    }
  }
}
