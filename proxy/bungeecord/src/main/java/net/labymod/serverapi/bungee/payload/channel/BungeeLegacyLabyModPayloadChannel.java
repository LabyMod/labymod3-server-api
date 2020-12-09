package net.labymod.serverapi.bungee.payload.channel;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.netty.buffer.Unpooled;
import java.util.List;
import net.labymod.serverapi.api.extension.AddonExtension;
import net.labymod.serverapi.api.extension.ModificationExtension;
import net.labymod.serverapi.api.protocol.ChunkCachingProtocol;
import net.labymod.serverapi.api.protocol.ShadowProtocol;
import net.labymod.serverapi.bungee.event.BungeeLabyModPlayerLoginEvent;
import net.labymod.serverapi.bungee.event.BungeeMessageReceiveEvent;
import net.labymod.serverapi.bungee.event.BungeeReceivePayloadEvent;
import net.labymod.serverapi.common.extension.DefaultAddonExtensionCollector;
import net.labymod.serverapi.common.extension.DefaultModificationExtensionCollector;
import net.labymod.serverapi.common.payload.DefaultLegacyLabyModPayloadChannel;
import net.labymod.serverapi.common.payload.DefaultPayloadBuffer;
import net.labymod.serverapi.common.protocol.DefaultChunkCachingProtocolFactory;
import net.labymod.serverapi.common.protocol.DefaultShadowProtocolFactory;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class BungeeLegacyLabyModPayloadChannel extends DefaultLegacyLabyModPayloadChannel
    implements Listener {

  private static final JsonParser JSON_PARSER = new JsonParser();
  private final ProxyServer proxyServer;

  public BungeeLegacyLabyModPayloadChannel(ProxyServer proxyServer) {
    super(
        DefaultAddonExtensionCollector.getInstance(),
        DefaultModificationExtensionCollector.getInstance(),
        DefaultChunkCachingProtocolFactory.getInstance(),
        DefaultShadowProtocolFactory.getInstance());
    this.proxyServer = proxyServer;
  }

  @EventHandler
  public void legacyLabyMod(BungeeReceivePayloadEvent event) {
    if (!this.isLegacyChannel(event.getIdentifier())) {
      return;
    }

    ProxiedPlayer player = this.proxyServer.getPlayer(event.getUniqueId());

    if (player == null) {
      return;
    }

    this.readPayload(player, new DefaultPayloadBuffer(Unpooled.wrappedBuffer(event.getPayload())));
  }

  /** {@inheritDoc} */
  @Override
  public JsonElement parseMessageContent(String messageContent) {
    return JSON_PARSER.parse(messageContent);
  }

  /** {@inheritDoc} */
  @Override
  public <T> void onReceiveMessage(T player, String key, JsonElement content) {
    this.proxyServer
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
    this.proxyServer
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
