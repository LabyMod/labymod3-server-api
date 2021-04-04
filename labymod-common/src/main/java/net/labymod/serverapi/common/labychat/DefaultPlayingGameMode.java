package net.labymod.serverapi.common.labychat;

import com.google.gson.JsonObject;
import java.util.UUID;
import net.labymod.serverapi.api.LabyService;
import net.labymod.serverapi.api.labychat.PlayingGameMode;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.player.LabyModPlayerService;

public class DefaultPlayingGameMode implements PlayingGameMode {

  private static final String SERVER_GAMEMODE_CHANNEL = "server_gamemode";
  private final LabyModPlayerService<?> playerService;
  private final PayloadCommunicator payloadCommunicator;

  public DefaultPlayingGameMode(LabyService service) {
    this.payloadCommunicator = service.getPayloadCommunicator();
    this.playerService = service.getLabyPlayerService();
  }

  /** {@inheritDoc} */
  @Override
  public void sendCurrentPlayingGameMode(UUID uniqueId, boolean showGameMode, String gameModeName) {
    JsonObject currentPlayingGameModeObject = new JsonObject();
    currentPlayingGameModeObject.addProperty("show_gamemode", showGameMode);
    currentPlayingGameModeObject.addProperty("gamemode_name", gameModeName);

    this.payloadCommunicator.sendLabyModMessage(
        uniqueId, SERVER_GAMEMODE_CHANNEL, currentPlayingGameModeObject);
  }

  @Override
  public void broadcastCurrentlyPlayingGameMode(boolean visible, String gameModeName) {
    for (LabyModPlayer<?> player : this.playerService.getPlayers()) {
      this.sendCurrentPlayingGameMode(player.getUniqueId(), gameModeName);
    }
  }
}
