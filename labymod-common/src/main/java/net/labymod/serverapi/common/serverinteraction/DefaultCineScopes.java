package net.labymod.serverapi.common.serverinteraction;

import com.google.gson.JsonObject;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.UUID;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.serverinteraction.CineScopes;

@Singleton
public class DefaultCineScopes implements CineScopes {

  private static final String CINESCOPES_CHANNEL = "cinescopes";

  private final PayloadCommunicator payloadCommunicator;

  @Inject
  private DefaultCineScopes(PayloadCommunicator payloadCommunicator) {
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
