package net.labymod.serverapi.common.labychat;

import com.google.gson.JsonObject;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.UUID;
import net.labymod.serverapi.api.labychat.PlayingGameMode;
import net.labymod.serverapi.api.payload.PayloadCommunicator;

@Singleton
public class DefaultPlayingGameMode implements PlayingGameMode {

  private static final String SERVER_GAMEMODE_CHANNEL = "server_gamemode";
  private final PayloadCommunicator payloadCommunicator;

  @Inject
  private DefaultPlayingGameMode(PayloadCommunicator payloadCommunicator) {
    this.payloadCommunicator = payloadCommunicator;
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
}
