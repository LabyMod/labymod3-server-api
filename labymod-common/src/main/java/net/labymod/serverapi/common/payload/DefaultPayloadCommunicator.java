package net.labymod.serverapi.common.payload;

import com.google.gson.JsonElement;
import java.util.UUID;
import net.labymod.serverapi.api.payload.PayloadBuffer;
import net.labymod.serverapi.api.payload.PayloadBuffer.Factory;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.player.LabyModPlayerService;

public abstract class DefaultPayloadCommunicator implements PayloadCommunicator {

  private static final String LMC_CHANNEL = "LMC";
  private static final String MODERN_LEGACY_LMC_CHANNEL = "legacy:lmc";
  private static final String CCP_CHANNEL = "CCP";
  private static final String MODERN_LEGACY_CCP_CHANNEL = "legacy:ccp";
  private static final String SHADOW_CHANNEL = "shadow";
  private static final String MODERN_SHADOW_CHANNEL = "legacy:shadow";
  private static final String LAVA_UPDATE_CHANNEL = "lava_update";
  private static final String MODERN_LAVA_UPDATE_CHANNEL = "legacy:lava_update";

  private final LabyModPlayerService<?> labyModPlayerService;
  private final PayloadBuffer.Factory payloadBufferFactory;

  public DefaultPayloadCommunicator(
      LabyModPlayerService<?> labyModPlayerService, Factory payloadBufferFactory) {
    this.labyModPlayerService = labyModPlayerService;
    this.payloadBufferFactory = payloadBufferFactory;
  }

  /** {@inheritDoc} */
  @Override
  public void sendChunkCachingProtocolMessage(UUID uniqueId, byte[] payload) {
    this.labyModPlayerService
        .getPlayer(uniqueId)
        .ifPresent(
            player -> sendCustomPayload(player, CCP_CHANNEL, MODERN_LEGACY_CCP_CHANNEL, payload));
  }

  /** {@inheritDoc} */
  @Override
  public void sendLabyModMessage(UUID uniqueId, String messageKey, JsonElement messageContent) {
    this.labyModPlayerService
        .getPlayer(uniqueId)
        .ifPresent(
            player -> {
              PayloadBuffer payloadBuffer = payloadBufferFactory.create();

              payloadBuffer.writeString(messageKey);
              payloadBuffer.writeString(messageContent.toString());

              sendCustomPayload(
                  player, LMC_CHANNEL, MODERN_LEGACY_LMC_CHANNEL, payloadBuffer.getBytes());
            });
  }

  /** {@inheritDoc} */
  @Override
  public void sendLavaUpdateProtocolMessage(UUID uniqueId, byte[] payload) {
    this.labyModPlayerService
        .getPlayer(uniqueId)
        .ifPresent(
            player ->
                sendCustomPayload(
                    player, LAVA_UPDATE_CHANNEL, MODERN_LAVA_UPDATE_CHANNEL, payload));
  }

  /** {@inheritDoc} */
  @Override
  public void sendShadowProtocolMessage(UUID uniqueId, byte[] payload) {
    this.labyModPlayerService
        .getPlayer(uniqueId)
        .ifPresent(
            player -> sendCustomPayload(player, SHADOW_CHANNEL, MODERN_SHADOW_CHANNEL, payload));
  }

  private void sendCustomPayload(
      LabyModPlayer<?> player, String oldChannel, String legacyChannel, byte[] payload) {

    String version = player.getVersion();

    if (this.isLabyModV3(version) && !this.shouldSupportModernChannel(version)) {
      this.send(player.getUniqueId(), oldChannel, payload);
      return;
    }

    this.send(player.getUniqueId(), legacyChannel, payload);
  }

  private boolean isLabyModV3(String version) {
    int[] splitVersion = this.splitVersion(version);
    return splitVersion[0] == 3;
  }

  private boolean shouldSupportModernChannel(String version) {
    int[] splitVersion = this.splitVersion(version);
    int[] nonLegacySupportVersion = LabyModPlayerService.NON_LEGACY_SUPPORT_VERSION;
    return splitVersion[0] == 3
        && nonLegacySupportVersion[1] < splitVersion[1]
        && nonLegacySupportVersion[2] < splitVersion[2];
  }

  @SuppressWarnings("ConstantConditions")
  private int[] splitVersion(String version) {
    if (!version.contains(".")) {
      throw new IllegalStateException(String.format("Not valid version! (%s)", version));
    }

    int[] versionArray = new int[3];
    String[] split = version.split("\\.");

    for (int i = 0; i < split.length; i++) {

      int versionNumber;
      try {
        versionNumber = Integer.parseInt(split[i]);
      } catch (NumberFormatException exception) {
        throw new IllegalArgumentException(String.format("Not valid version! (%s)", version));
      }

      if (i <= 2) {
        versionArray[i] = versionNumber;
      }
    }

    if (split.length < 3) {
      versionArray[2] = 0;
    }

    return versionArray;
  }
}
