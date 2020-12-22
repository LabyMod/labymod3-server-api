package net.labymod.serverapi.bungee.payload.channel;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.netty.buffer.Unpooled;
import java.util.List;
import net.labymod.serverapi.api.extension.AddonExtension;
import net.labymod.serverapi.api.extension.ExtensionCollector;
import net.labymod.serverapi.api.extension.ModificationExtension;
import net.labymod.serverapi.api.payload.PayloadBuffer;
import net.labymod.serverapi.api.protocol.ChunkCachingProtocol;
import net.labymod.serverapi.api.protocol.ChunkCachingProtocol.Factory;
import net.labymod.serverapi.api.protocol.ShadowProtocol;
import net.labymod.serverapi.bungee.BungeeLabyModPlugin;
import net.labymod.serverapi.bungee.event.BungeeLabyModPlayerLoginEvent;
import net.labymod.serverapi.bungee.event.BungeeMessageReceiveEvent;
import net.labymod.serverapi.bungee.event.BungeeReceivePayloadEvent;
import net.labymod.serverapi.common.payload.DefaultLegacyLabyModPayloadChannel;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

@Singleton
public class BungeeLegacyLabyModPayloadChannel extends DefaultLegacyLabyModPayloadChannel
    implements Listener {

  private static final JsonParser JSON_PARSER = new JsonParser();
  private final BungeeLabyModPlugin plugin;
  private final PayloadBuffer.Factory payloadBufferFactory;

  @Inject
  private BungeeLegacyLabyModPayloadChannel(
      ExtensionCollector<AddonExtension> addonExtensionCollector,
      ExtensionCollector<ModificationExtension> modificationExtensionCollector,
      Factory chunkCachingProtocolFactory,
      ShadowProtocol.Factory shadowProtocolFactory,
      BungeeLabyModPlugin plugin,
      PayloadBuffer.Factory payloadBufferFactory) {
    super(
        addonExtensionCollector,
        modificationExtensionCollector,
        chunkCachingProtocolFactory,
        shadowProtocolFactory);
    this.plugin = plugin;
    this.payloadBufferFactory = payloadBufferFactory;
  }

  @EventHandler
  public void legacyLabyMod(BungeeReceivePayloadEvent event) {
    if (!this.isLegacyLMCChannel(event.getIdentifier())) {
      return;
    }

    ProxiedPlayer player = this.plugin.getProxy().getPlayer(event.getUniqueId());

    if (player == null) {
      return;
    }

    this.readPayload(
        player, this.payloadBufferFactory.create(Unpooled.wrappedBuffer(event.getPayload())));
  }

  /** {@inheritDoc} */
  @Override
  public JsonElement parseMessageContent(String messageContent) {
    return JSON_PARSER.parse(messageContent);
  }

  /** {@inheritDoc} */
  @Override
  public <T> void onReceiveMessage(T player, String key, JsonElement content) {
    this.plugin
        .getProxy()
        .getPluginManager()
        .callEvent(new BungeeMessageReceiveEvent((ProxiedPlayer) player, key, content));
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
    this.plugin
        .getProxy()
        .getPluginManager()
        .callEvent(
            new BungeeLabyModPlayerLoginEvent(
                (ProxiedPlayer) player,
                addons,
                modifications,
                version,
                chunkCachingProtocol,
                shadowProtocol));
  }
}
