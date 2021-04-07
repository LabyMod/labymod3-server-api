package net.labymod.serverapi.common.labychat;

import com.google.gson.JsonObject;
import java.util.UUID;
import net.labymod.serverapi.api.LabyService;
import net.labymod.serverapi.api.labychat.PlayingGameModeTransmitter;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.player.LabyModPlayerService;

/** Default implementation of the {@link PlayingGameModeTransmitter}. */
public class DefaultPlayingGameModeTransmitter implements PlayingGameModeTransmitter {

  private static final String SERVER_GAMEMODE_CHANNEL = "server_gamemode";
  private final LabyModPlayerService<?> playerService;
  private final PayloadCommunicator payloadCommunicator;

  public DefaultPlayingGameModeTransmitter(LabyService service) {
    this.payloadCommunicator = service.getPayloadCommunicator();
    this.playerService = service.getLabyPlayerService();
  }

  /** {@inheritDoc} */
  @Override
  public void transmit(UUID uniqueId, boolean visible, String gameModeName) {
    JsonObject currentPlayingGameModeObject = new JsonObject();
    currentPlayingGameModeObject.addProperty("show_gamemode", visible);
    currentPlayingGameModeObject.addProperty("gamemode_name", gameModeName);

    this.payloadCommunicator.sendLabyModMessage(
        uniqueId, SERVER_GAMEMODE_CHANNEL, currentPlayingGameModeObject);
  }

  /** {@inheritDoc} */
  @Override
  public void broadcastTransmit(boolean visible, String gameModeName) {
    for (LabyModPlayer<?> player : this.playerService.getPlayers()) {
      this.transmit(player.getUniqueId(), visible, gameModeName);
    }
  }
}
