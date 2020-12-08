package net.labymod.serverapi.common.payload;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.List;
import java.util.logging.Logger;
import net.labymod.serverapi.api.extension.AddonExtension;
import net.labymod.serverapi.api.extension.ExtensionCollector;
import net.labymod.serverapi.api.extension.ModificationExtension;
import net.labymod.serverapi.api.payload.PayloadBuffer;
import net.labymod.serverapi.api.protocol.ChunkCachingProtocol;
import net.labymod.serverapi.api.protocol.Protocol;
import net.labymod.serverapi.api.protocol.ShadowProtocol;

public abstract class DefaultLegacyLabyModPayloadChannel {

  private static final Logger LOGGER = Logger.getLogger("LMC Channel");

  private final ExtensionCollector<AddonExtension> addonExtensionCollector;
  private final ExtensionCollector<ModificationExtension> modificationExtensionCollector;
  private final ChunkCachingProtocol.Factory chunkCachingProtocolFactory;
  private final ShadowProtocol.Factory shadowProtocolFactory;

  public DefaultLegacyLabyModPayloadChannel(
      ExtensionCollector<AddonExtension> addonExtensionCollector,
      ExtensionCollector<ModificationExtension> modificationExtensionCollector,
      ChunkCachingProtocol.Factory chunkCachingProtocolFactory,
      ShadowProtocol.Factory shadowProtocolFactory) {
    this.addonExtensionCollector = addonExtensionCollector;
    this.modificationExtensionCollector = modificationExtensionCollector;
    this.chunkCachingProtocolFactory = chunkCachingProtocolFactory;
    this.shadowProtocolFactory = shadowProtocolFactory;
  }

  /**
   * Whether the specified identifier is the LMC channel.
   *
   * @param identifier The identifier to be checked.
   * @return {@code true} if the specified identifier is the LMC channel, otherwise {@code false}.
   */
  public boolean isLegacyChannel(String identifier) {
    return identifier.equals("LMC") || identifier.equals("legacy:lmc");
  }

  /**
   * Reads the incoming payload messages.
   *
   * @param player The player who receives the incoming payload messages.
   * @param payloadBuffer The payload buffer to read the incoming payload messages.
   * @param <T> The player type of the implemented software.
   */
  public <T> void readPayload(T player, PayloadBuffer payloadBuffer) {
    String messageKey = payloadBuffer.readString();
    String messageContent = payloadBuffer.readString();

    JsonElement jsonElement;
    try {
      jsonElement = this.parseMessageContent(messageContent);
    } catch (JsonParseException exception) {
      LOGGER.severe(
          String.format("The specified JSON string is not a valid one! (%s)", messageContent));
      return;
    }

    if (!messageKey.equals("INFO") && jsonElement.isJsonObject()) {
      this.onReceiveMessage(player, messageKey, jsonElement);
      return;
    }

    JsonObject object = jsonElement.getAsJsonObject();
    String version = null;

    if (object.has("version")) {
      version = object.get("version").getAsString();
    }

    if (version != null) {
      this.onLabyModPlayerJoin(
          player,
          this.addonExtensionCollector.collect(object),
          this.modificationExtensionCollector.collect(object),
          version,
          this.getProtocol("ccp", object),
          this.getProtocol("shadow", object));
    }
  }

  /**
   * Parses the specified `JSON` string into a parse tree.
   *
   * @param messageContent The JSON text.
   * @return A parse tree of {@link JsonElement}'s corresponding to the specified JSON.
   * @throws JsonParseException If the specified text is not valid JSON.
   */
  public abstract JsonElement parseMessageContent(String messageContent);

  /**
   * Is called when a LabyMod player has received a message.
   *
   * @param player The player who received the message.
   * @param key The key of the message.
   * @param content The content of the message.
   * @param <T> The player type of the implemented software.
   */
  public abstract <T> void onReceiveMessage(T player, String key, JsonElement content);

  /**
   * Is called when a LabyMod player joins the server.
   *
   * @param player The player who joining the server.
   * @param addons A collection of all installed addons.
   * @param modifications A collection fo all installed modifications.
   * @param version The version of the LabyMod client.
   * @param chunkCachingProtocol The chunk caching protocol of the joining player.
   * @param shadowProtocol The shadow protocol of the joining player.
   * @param <T> The player type of the implemented software.
   */
  public abstract <T> void onLabyModPlayerJoin(
      T player,
      List<AddonExtension> addons,
      List<ModificationExtension> modifications,
      String version,
      ChunkCachingProtocol chunkCachingProtocol,
      ShadowProtocol shadowProtocol);

  @SuppressWarnings("unchecked")
  private <T extends Protocol> T getProtocol(String protocolName, JsonObject protocolObject) {
    int version = 0;
    boolean enabled = false;

    if(protocolObject.has(protocolName) && protocolObject.get(protocolName).isJsonObject()) {
      JsonObject protocol = protocolObject.get(protocolName).getAsJsonObject();


      if (protocol.has("version")) {
        version = protocol.get("version").getAsInt();
      }

      if (protocol.has("enabled")) {
        enabled = protocol.get("enabled").getAsBoolean();
      }

    }

    switch (protocolName) {
      case "ccp":
        return (T) this.chunkCachingProtocolFactory.create(version, enabled);
      case "shadow":
        return (T) this.shadowProtocolFactory.create(version, enabled);
      default:
        throw new IllegalStateException("Unexpected protocol: " + protocolName);
    }
  }
}
