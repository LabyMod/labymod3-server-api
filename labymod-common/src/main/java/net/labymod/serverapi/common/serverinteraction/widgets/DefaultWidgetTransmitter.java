package net.labymod.serverapi.common.serverinteraction.widgets;

import com.google.gson.JsonElement;
import java.util.UUID;
import net.labymod.serverapi.api.LabyService;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.api.serverinteraction.widgets.WidgetTransmitter;

public class DefaultWidgetTransmitter implements WidgetTransmitter {

  private static final String SCREEN_CHANNEL = "screen";

  private final LabyModPlayerService<?> playerService;
  private final PayloadCommunicator payloadCommunicator;

  public DefaultWidgetTransmitter(LabyService labyService) {
    this.payloadCommunicator = labyService.getPayloadCommunicator();
    this.playerService = labyService.getLabyPlayerService();
  }

  /** {@inheritDoc} */
  @Override
  public void transmit(UUID uniqueId, JsonElement screenElement) {
    this.payloadCommunicator.sendLabyModMessage(uniqueId, SCREEN_CHANNEL, screenElement);
  }

  /** {@inheritDoc} */
  @Override
  public void broadcastTransmit(JsonElement screenElement) {
    for (LabyModPlayer<?> player : this.playerService.getPlayers()) {
      this.transmit(player.getUniqueId(), screenElement);
    }
  }
}
