package net.labymod.serverapi.common.serverinteraction.gui;

import com.google.gson.JsonObject;
import net.labymod.serverapi.api.LabyService;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.api.serverinteraction.gui.InputPromptTransmitter;

import java.util.UUID;

public class DefaultInputPromptTransmitter implements InputPromptTransmitter {
  private static final String INPUT_PROMPT_CHANNEL = "input_prompt";
  private final PayloadCommunicator communicator;
  private final LabyModPlayerService<?> playerService;

  public DefaultInputPromptTransmitter(LabyService service) {
    this.communicator = service.getPayloadCommunicator();
    this.playerService = service.getLabyPlayerService();
  }

  @Override
  public void transmit(
      UUID uniqueId,
      int promptSessionId,
      JsonObject rawText,
      String value,
      String placeholder,
      int maximalLength) {

    JsonObject object = new JsonObject();
    object.addProperty("id", promptSessionId);
    object.addProperty("raw_json_text", rawText.toString());
    object.addProperty("value", value);
    object.addProperty("placeholder", placeholder);
    object.addProperty("max_length", maximalLength);

    this.communicator.sendLabyModMessage(uniqueId, INPUT_PROMPT_CHANNEL, object);
  }

  @Override
  public void transmit(
      UUID uniqueId,
      int promptSessionId,
      String message,
      String value,
      String placeholder,
      int maximalLength) {

    JsonObject object = new JsonObject();
    object.addProperty("id", promptSessionId);
    object.addProperty("message", message);
    object.addProperty("value", value);
    object.addProperty("placeholder", placeholder);
    object.addProperty("max_length", maximalLength);

    this.communicator.sendLabyModMessage(uniqueId, INPUT_PROMPT_CHANNEL, object);
  }

  @Override
  public void broadcastTransmit(
      int promptSessionId,
      JsonObject rawText,
      String value,
      String placeholder,
      int maximalLength) {
    for (LabyModPlayer<?> player : this.playerService.getPlayers()) {
      this.transmit(
          player.getUniqueId(), promptSessionId, rawText, value, placeholder, maximalLength);
    }
  }

  @Override
  public void broadcastTransmit(
      int promptSessionId, String message, String value, String placeholder, int maximalLength) {
    for (LabyModPlayer<?> player : this.playerService.getPlayers()) {
      this.transmit(
          player.getUniqueId(), promptSessionId, message, value, placeholder, maximalLength);
    }
  }
}
