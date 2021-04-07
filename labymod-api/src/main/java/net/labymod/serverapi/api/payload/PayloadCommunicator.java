package net.labymod.serverapi.api.payload;

import com.google.gson.JsonElement;
import java.util.UUID;

public interface PayloadCommunicator {

  /**
   * Sends a custom payload into the given channel {@code identifier}.
   *
   * @param uniqueId The unique identifier of a player who should send the payload.
   * @param identifier The channel identifier where the payload should be sent to .
   * @param payload The payload as a byte array.
   */
  void send(UUID uniqueId, String identifier, byte[] payload);

  /**
   * Sends a custom payload into the chunk caching protocol channel.
   *
   * @param uniqueId THe unique identifier of a player who should send the payload.
   * @param payload The payload as a byte array.
   */
  void sendChunkCachingProtocolMessage(UUID uniqueId, byte[] payload);

  /**
   * Sends a custom payload into the shadow protocol channel.
   *
   * @param uniqueId The unique identifier of a player who should send the payload.
   * @param payload The payload as a byte array.
   */
  void sendShadowProtocolMessage(UUID uniqueId, byte[] payload);

  /**
   * Sends a custom payload into the lava update protocol channel.
   *
   * @param uniqueId The unique identifier of a player who should send the payload.
   * @param payload The payload as a byte array.
   */
  void sendLavaUpdateProtocolMessage(UUID uniqueId, byte[] payload);

  /**
   * Sends a custom payload as a json tree into the lmc channel.
   *
   * @param uniqueId The unique identifier of a player who should send the payload.
   * @param messageKey The key of the message.
   * @param messageContent The content for the message as a json tree.
   */
  void sendLabyModMessage(UUID uniqueId, String messageKey, JsonElement messageContent);

  /**
   * Receives a custom payload from the given channel {@code identifier}.
   *
   * @param uniqueId The unique identifier of a player who should receive the payload.
   * @param identifier The channel identifier to which the payload was sent.
   * @param payload The payload as a byte array.
   */
  void receive(UUID uniqueId, String identifier, byte[] payload);
}
