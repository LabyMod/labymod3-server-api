package net.labymod.serverapi.common.serverinteraction;

import com.google.gson.JsonObject;
import java.util.UUID;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.serverinteraction.CineScopes;

public class DefaultCineScopes implements CineScopes {

  private static final String CINESCOPES_CHANNEL = "cinescopes";

  private final PayloadCommunicator payloadCommunicator;

  public DefaultCineScopes(PayloadCommunicator payloadCommunicator) {
    this.payloadCommunicator = payloadCommunicator;
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
}
