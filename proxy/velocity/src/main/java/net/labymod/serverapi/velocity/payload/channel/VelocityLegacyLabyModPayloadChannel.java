package net.labymod.serverapi.velocity.payload.channel;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import io.netty.buffer.Unpooled;
import java.util.List;
import java.util.Optional;
import net.labymod.serverapi.api.extension.AddonExtension;
import net.labymod.serverapi.api.extension.ExtensionCollector;
import net.labymod.serverapi.api.extension.ModificationExtension;
import net.labymod.serverapi.api.payload.PayloadBuffer;
import net.labymod.serverapi.api.protocol.ChunkCachingProtocol;
import net.labymod.serverapi.api.protocol.ChunkCachingProtocol.Factory;
import net.labymod.serverapi.api.protocol.ShadowProtocol;
import net.labymod.serverapi.common.payload.DefaultLegacyLabyModPayloadChannel;
import net.labymod.serverapi.velocity.event.VelocityLabyModPlayerLoginEvent;
import net.labymod.serverapi.velocity.event.VelocityMessageReceiveEvent;
import net.labymod.serverapi.velocity.event.VelocityReceivePayloadEvent;

public class VelocityLegacyLabyModPayloadChannel extends DefaultLegacyLabyModPayloadChannel {

  private final ProxyServer proxyServer;
  private final PayloadBuffer.Factory payloadBufferFactory;

  @Inject
  private VelocityLegacyLabyModPayloadChannel(
      ExtensionCollector<AddonExtension> addonExtensionCollector,
      ExtensionCollector<ModificationExtension> modificationExtensionCollector,
      Factory chunkCachingProtocolFactory,
      ShadowProtocol.Factory shadowProtocolFactory,
      ProxyServer proxyServer,
      PayloadBuffer.Factory payloadBufferFactory) {
    super(
        addonExtensionCollector,
        modificationExtensionCollector,
        chunkCachingProtocolFactory,
        shadowProtocolFactory);
    this.proxyServer = proxyServer;
    this.payloadBufferFactory = payloadBufferFactory;
  }

  @Subscribe
  public void legacyLabyMod(VelocityReceivePayloadEvent event) {
    if (!this.isLegacyLMCChannel(event.getIdentifier())) {
      return;
    }

    Optional<Player> optionalPlayer = this.proxyServer.getPlayer(event.getUniqueId());

    if (!optionalPlayer.isPresent()) {
      return;
    }

    Player player = optionalPlayer.get();

    this.readPayload(player, this.payloadBufferFactory.create(Unpooled.wrappedBuffer(event.getPayload())));
  }

  /** {@inheritDoc} */
  @Override
  public JsonElement parseMessageContent(String messageContent) {
    return JsonParser.parseString(messageContent);
  }

  /** {@inheritDoc} */
  @Override
  public <T> void onReceiveMessage(T player, String key, JsonElement content) {
    this.proxyServer
        .getEventManager()
        .fire(new VelocityMessageReceiveEvent((Player) player, key, content));
  }

  /** {@inheritDoc} */
  @Override
  public <T> void onLabyModPlayerJoin(
      T player,
      List<AddonExtension> addons,
      List<ModificationExtension> modifications,
      String version,
      ChunkCachingProtocol chunkCachingProtocol,
      ShadowProtocol shadowProtocol) {
    this.proxyServer
        .getEventManager()
        .fire(
            new VelocityLabyModPlayerLoginEvent(
                (Player) player,
                addons,
                modifications,
                version,
                chunkCachingProtocol,
                shadowProtocol));
  }
}
