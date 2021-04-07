package net.labymod.serverapi.common.serverinteraction;

import com.google.gson.JsonObject;
import java.util.UUID;
import net.labymod.serverapi.api.LabyService;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.api.serverinteraction.ServerSwitcherTransmitter;

public class DefaultServerSwitcherTransmitter implements ServerSwitcherTransmitter {

  private static final String SERVER_SWITCH_CHANNEL = "server_switch";
  private final PayloadCommunicator payloadCommunicator;
  private final LabyModPlayerService<?> playerService;

  public DefaultServerSwitcherTransmitter(LabyService service) {
    this.payloadCommunicator = service.getPayloadCommunicator();
    this.playerService = service.getLabyPlayerService();
  }

  /** {@inheritDoc} */
  @Override
  public void transmit(UUID uniqueId, String title, String address, boolean preview) {
    JsonObject serverSwitchObject = new JsonObject();

    serverSwitchObject.addProperty("title", title);
    serverSwitchObject.addProperty("address", address);
    serverSwitchObject.addProperty("preview", preview);

    this.payloadCommunicator.sendLabyModMessage(
        uniqueId, SERVER_SWITCH_CHANNEL, serverSwitchObject);
  }

  /** {@inheritDoc} */
  @Override
  public void broadcastTransmit(String title, String address, boolean preview) {
    for (LabyModPlayer<?> player : this.playerService.getPlayers()) {
      this.transmit(player.getUniqueId(), title, address, preview);
    }
  }
}
