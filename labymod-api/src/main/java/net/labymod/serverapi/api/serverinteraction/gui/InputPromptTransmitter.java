package net.labymod.serverapi.api.serverinteraction.gui;

import com.google.gson.JsonObject;
import net.labymod.serverapi.api.player.LabyModPlayer;

public interface InputPromptTransmitter {

  void transmit(
      LabyModPlayer<?> player,
      int promptSessionId,
      JsonObject rawText,
      String value,
      String placeholder,
      int maximalLength);

  void transmit(
      LabyModPlayer<?> player,
      int promptSessionId,
      String message,
      String value,
      String placeholder,
      int maximalLength);
}
