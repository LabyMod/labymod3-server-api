package net.labymod.serverapi.common.payload;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.UUID;
import net.labymod.serverapi.api.LabyDebugger;
import net.labymod.serverapi.api.LabyService;
import net.labymod.serverapi.api.payload.PayloadBuffer;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.player.LabyModPlayerService;

public abstract class DefaultPayloadCommunicator implements PayloadCommunicator {

  private static final String MODERN_LEGACY_LMC_CHANNEL = "labymod3:main";
  private static final String MODERN_LEGACY_CCP_CHANNEL = "labymod3:ccp";
  private static final String MODERN_SHADOW_CHANNEL = "labymod3:shadow";
  private static final String MODERN_LAVA_UPDATE_CHANNEL = "labymod3:lava_update";

  private static final String SERVER_API_VERSION_KEY = "server_api";

  protected final LabyDebugger debugger;
  private final LabyModPlayerService<?> labyModPlayerService;
  private final PayloadBuffer.Factory payloadBufferFactory;

  private final String pluginVersion;

  public DefaultPayloadCommunicator(LabyService service) {
    this.labyModPlayerService = service.getLabyPlayerService();
    this.payloadBufferFactory = new DefaultPayloadBufferFactory();
    this.pluginVersion = service.getVersion();
    this.debugger = service.getLabyDebugger();
  }

  /** {@inheritDoc} */
  @Override
  public void sendChunkCachingProtocolMessage(UUID uniqueId, byte[] payload) {
    this.labyModPlayerService
        .getPlayer(uniqueId)
        .ifPresent(player -> sendCustomPayload(player, MODERN_LEGACY_CCP_CHANNEL, payload));
  }

  /** {@inheritDoc} */
  @Override
  public void sendLabyModMessage(UUID uniqueId, String messageKey, JsonElement messageContent) {
    this.labyModPlayerService
        .getPlayer(uniqueId)
        .ifPresent(
            player -> {
              try {
                PayloadBuffer payloadBuffer = payloadBufferFactory.create();

                payloadBuffer.writeString(messageKey);
                payloadBuffer.writeString(messageContent.toString());

                sendCustomPayload(player, MODERN_LEGACY_LMC_CHANNEL, payloadBuffer.getBytes());
              } catch (Exception exception) {
                this.debugger.error(
                    "An error occurred while sending the payload message.", exception);
              }
            });
  }

  /** {@inheritDoc} */
  @Override
  public void sendLavaUpdateProtocolMessage(UUID uniqueId, byte[] payload) {
    this.labyModPlayerService
        .getPlayer(uniqueId)
        .ifPresent(player -> sendCustomPayload(player, MODERN_LAVA_UPDATE_CHANNEL, payload));
  }

  /** {@inheritDoc} */
  @Override
  public void sendShadowProtocolMessage(UUID uniqueId, byte[] payload) {
    this.labyModPlayerService
        .getPlayer(uniqueId)
        .ifPresent(player -> sendCustomPayload(player, MODERN_SHADOW_CHANNEL, payload));
  }

  /** {@inheritDoc} */
  @Override
  public void sendServerApiMessage(UUID uniqueId) {
    JsonObject object = new JsonObject();
    object.addProperty("version", this.pluginVersion);
    this.sendLabyModMessage(uniqueId, SERVER_API_VERSION_KEY, object);
  }

  private void sendCustomPayload(LabyModPlayer<?> player, String payloadChannel, byte[] payload) {
    this.send(player.getUniqueId(), payloadChannel, payload);
  }
}
