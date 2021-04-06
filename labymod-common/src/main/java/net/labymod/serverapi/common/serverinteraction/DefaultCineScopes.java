package net.labymod.serverapi.common.serverinteraction;

import com.google.gson.JsonObject;
import net.labymod.serverapi.api.LabyService;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.api.serverinteraction.CineScopes;

import java.util.UUID;

public class DefaultCineScopes implements CineScopes {

  private static final String CINESCOPES_CHANNEL = "cinescopes";

  private final LabyModPlayerService<?> playerService;
  private final PayloadCommunicator payloadCommunicator;

  public DefaultCineScopes(LabyService service) {
    this.payloadCommunicator = service.getPayloadCommunicator();
    this.playerService = service.getLabyPlayerService();
  }

  /** {@inheritDoc} */
  @Override
  public void sendCineScope(
      UUID uniqueId, boolean showCineScopes, int coveragePercent, long duration) {
    JsonObject cineScopeObject = new JsonObject();

    if (coveragePercent < 1) {
      coveragePercent = 1;
    } else if (coveragePercent > 50) {
      coveragePercent = 50;
    }

    cineScopeObject.addProperty("visible", showCineScopes);
    cineScopeObject.addProperty("coverage", coveragePercent);
    cineScopeObject.addProperty("duration", duration);

    this.payloadCommunicator.sendLabyModMessage(uniqueId, CINESCOPES_CHANNEL, cineScopeObject);
  }

  @Override
  public void broadcastSendCineScope(boolean showCineScopes, int coveragePercent, long duration) {
    for (LabyModPlayer<?> player : this.playerService.getPlayers()) {
      this.sendCineScope(player.getUniqueId(), showCineScopes, coveragePercent, duration);
    }
  }
}
