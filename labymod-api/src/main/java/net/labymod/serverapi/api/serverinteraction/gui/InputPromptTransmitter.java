package net.labymod.serverapi.api.serverinteraction.gui;

import com.google.gson.JsonObject;
import java.util.UUID;

public interface InputPromptTransmitter {

  void transmit(
      UUID uniqueId,
      int promptSessionId,
      JsonObject rawText,
      String value,
      String placeholder,
      int maximalLength);

  void transmit(
      UUID uniqueId,
      int promptSessionId,
      String message,
      String value,
      String placeholder,
      int maximalLength);

  void broadcastTransmit(
      int promptSessionId,
      JsonObject rawText,
      String value,
      String placeholder,
      int maximalLength);

  void broadcastTransmit(
      int promptSessionId,
      String message,
      String value,
      String placeholder,
      int maximalLength);
}
