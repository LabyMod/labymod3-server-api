package net.labymod.serverapi.common.serverinteraction;

import com.google.gson.JsonObject;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.UUID;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.serverinteraction.ServerSwitcher;

@Singleton
public class DefaultServerSwitcher implements ServerSwitcher {

  private static final String SERVER_SWITCH_CHANNEL = "server_switch";

  private final PayloadCommunicator payloadCommunicator;

  @Inject
  private DefaultServerSwitcher(PayloadCommunicator payloadCommunicator) {
    this.payloadCommunicator = payloadCommunicator;
  }

  /** {@inheritDoc} */
  @Override
  public void sendPlayerToServer(UUID uniqueId, String title, String address, boolean preview) {
    JsonObject serverSwitchObject = new JsonObject();

    serverSwitchObject.addProperty("title", title);
    serverSwitchObject.addProperty("address", address);
    serverSwitchObject.addProperty("preview", preview);

    this.payloadCommunicator.sendLabyModMessage(
        uniqueId, SERVER_SWITCH_CHANNEL, serverSwitchObject);
  }
}
