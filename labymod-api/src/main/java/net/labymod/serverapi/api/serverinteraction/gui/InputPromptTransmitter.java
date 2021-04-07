package net.labymod.serverapi.api.serverinteraction.gui;

import com.google.gson.JsonObject;
import java.util.UUID;

/**
 * Represents the input prompt packet.
 *
 * <p><b>Overview:</b>
 *
 * <p>With this feature it is possible to request a text input from a player. The server can send
 * any text with any pre-entered input to the client.
 */
public interface InputPromptTransmitter {

  /**
   * Transmits the input prompt to the client with the specified {@code uniqueId}.
   *
   * @param uniqueId The unique identifier of the receiver.
   * @param promptSessionId The prompt session identifier.
   * @param rawText The message above the text field as a {@link JsonObject}.
   * @param value The value inside of the text field.
   * @param placeholder A placeholder text inside of the field if there is no value given.
   * @param maximumAmount The maximum amount of character of the text field value.
   */
  void transmit(
      UUID uniqueId,
      int promptSessionId,
      JsonObject rawText,
      String value,
      String placeholder,
      int maximumAmount);

  /**
   * Transmits the input prompt to the client with the specified {@code uniqueId}.
   *
   * @param uniqueId The unique identifier of the receiver.
   * @param promptSessionId The prompt session identifier.
   * @param message The message above the text field.
   * @param value The value inside of the text field.
   * @param placeholder The placeholder text inside of the field if there is not value given.
   * @param maximumAmount The maximum amount of character of the text field value.
   */
  void transmit(
      UUID uniqueId,
      int promptSessionId,
      String message,
      String value,
      String placeholder,
      int maximumAmount);

  /**
   * Transmits the input prompt to all online laby users.
   *
   * @param promptSessionId The prompt session identifier.
   * @param rawText The message above the text field as a {@link JsonObject}.
   * @param value The value inside of the text field.
   * @param placeholder The placeholder text inside of the field if there is not value given.
   * @param maximumAmount The maximum amount of character of the text field value.
   */
  void broadcastTransmit(
      int promptSessionId, JsonObject rawText, String value, String placeholder, int maximumAmount);

  /**
   * Transmits the input prompt to all online laby users.
   *
   * @param promptSessionId The prompt session identifier.
   * @param message The message above the text field.
   * @param value The value inside of the text field.
   * @param placeholder The placeholder text inside of the field if there is not value given.
   * @param maximumAmount The maximum amount of character of the text field value.
   */
  void broadcastTransmit(
      int promptSessionId, String message, String value, String placeholder, int maximumAmount);
}
