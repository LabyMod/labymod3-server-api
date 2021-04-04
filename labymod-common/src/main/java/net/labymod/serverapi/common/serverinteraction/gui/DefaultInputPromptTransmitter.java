package net.labymod.serverapi.common.serverinteraction.gui;

import com.google.gson.JsonObject;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.serverinteraction.gui.InputPromptTransmitter;

@Singleton
public class DefaultInputPromptTransmitter implements InputPromptTransmitter {
  private static final String INPUT_PROMPT_CHANNEL = "input_prompt";
  private final PayloadCommunicator communicator;

  @Inject
  private DefaultInputPromptTransmitter(PayloadCommunicator communicator) {
    this.communicator = communicator;
  }

  @Override
  public void transmit(
      LabyModPlayer<?> player,
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

    this.communicator.sendLabyModMessage(player.getUniqueId(), INPUT_PROMPT_CHANNEL, object);
  }

  @Override
  public void transmit(
      LabyModPlayer<?> player,
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

    this.communicator.sendLabyModMessage(player.getUniqueId(), INPUT_PROMPT_CHANNEL, object);
  }
}
