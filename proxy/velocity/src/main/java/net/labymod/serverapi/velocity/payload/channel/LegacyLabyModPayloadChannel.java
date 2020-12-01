package net.labymod.serverapi.velocity.payload.channel;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import net.labymod.serverapi.api.extension.AddonExtension;
import net.labymod.serverapi.api.extension.ExtensionCollector;
import net.labymod.serverapi.api.extension.ModificationExtension;
import net.labymod.serverapi.api.payload.PayloadBuffer;
import net.labymod.serverapi.api.protocol.ChunkCachingProtocol;
import net.labymod.serverapi.api.protocol.ChunkCachingProtocol.Factory;
import net.labymod.serverapi.api.protocol.ShadowProtocol;
import net.labymod.serverapi.velocity.event.LabyPlayerJoinEvent;
import net.labymod.serverapi.velocity.event.MessageReceiveEvent;
import net.labymod.serverapi.velocity.payload.event.ReceivePlayerPayloadEvent;

public class LegacyLabyModPayloadChannel {

  private static final JsonParser JSON_PARSER = new JsonParser();

  private final ProxyServer proxyServer;

  private final ExtensionCollector<AddonExtension> addonExtensionCollector;
  private final ExtensionCollector<ModificationExtension> modificationExtensionCollector;
  private final ChunkCachingProtocol.Factory chunkCachingProtocolFactory;
  private final ShadowProtocol.Factory shadowProtocolFactory;
  private final PayloadBuffer payloadBuffer;

  public LegacyLabyModPayloadChannel(
      ProxyServer proxyServer,
      ExtensionCollector<AddonExtension> addonExtensionCollector,
      ExtensionCollector<ModificationExtension> modificationExtensionCollector,
      Factory chunkCachingProtocolFactory,
      ShadowProtocol.Factory shadowProtocolFactory,
      PayloadBuffer payloadBuffer) {
    this.proxyServer = proxyServer;
    this.addonExtensionCollector = addonExtensionCollector;
    this.modificationExtensionCollector = modificationExtensionCollector;
    this.chunkCachingProtocolFactory = chunkCachingProtocolFactory;
    this.shadowProtocolFactory = shadowProtocolFactory;
    this.payloadBuffer = payloadBuffer;
  }

  @Subscribe
  public void legacyLabyMod(ReceivePlayerPayloadEvent event) {
    if (!event.getIdentifier().equals("LMC") || !event.getIdentifier().equals("legacy:lmc")) {
      return;
    }

    Optional<Player> optionalPlayer = this.proxyServer.getPlayer(event.getUniqueId());

    if (!optionalPlayer.isPresent()) {
      return;
    }

    Player player = optionalPlayer.get();

    String messageKey = this.payloadBuffer.readString();
    String messageContent = this.payloadBuffer.readString();

    JsonElement jsonElement = JSON_PARSER.parse(messageContent);

    if (!messageKey.equals("INFO") && !jsonElement.isJsonObject()) {
      this.proxyServer
          .getEventManager()
          .fire(new MessageReceiveEvent(player, messageKey, jsonElement));
      return;
    }

    JsonObject object = jsonElement.getAsJsonObject();

    AtomicReference<ChunkCachingProtocol> chunkCachingProtocol = new AtomicReference<>(null);
    AtomicReference<ShadowProtocol> shadowProtocol = new AtomicReference<>(null);

    this.getProtocol(
        (version, enabled) ->
            chunkCachingProtocol.set(chunkCachingProtocolFactory.create(version, enabled)),
        object,
        "ccp");

    this.getProtocol(
        (version, enabled) -> shadowProtocol.set(shadowProtocolFactory.create(version, enabled)),
        object,
        "shadow");

    this.proxyServer
        .getEventManager()
        .fire(
            new LabyPlayerJoinEvent(
                player,
                this.addonExtensionCollector.collect(object),
                this.modificationExtensionCollector.collect(object),
                object.get("version").getAsString(),
                chunkCachingProtocol.get(),
                shadowProtocol.get()));
  }

  private void getProtocol(
      BiConsumer<Integer, Boolean> callback, JsonObject object, String protocolName) {
    int version = 0;
    boolean enabled = false;

    if (object.has(protocolName) && object.get(protocolName).isJsonObject()) {
      JsonObject protocolObject = object.get(protocolName).getAsJsonObject();

      if (protocolObject.has("version")) {
        version = protocolObject.get("version").getAsInt();
      }

      if (protocolObject.has("enabled")) {
        enabled = protocolObject.get("enabled").getAsBoolean();
      }
    }

    if (callback != null) {
      callback.accept(version, enabled);
    }
  }
}
