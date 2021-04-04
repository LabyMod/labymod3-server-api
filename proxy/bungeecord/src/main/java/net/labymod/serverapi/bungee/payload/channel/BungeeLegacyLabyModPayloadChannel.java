package net.labymod.serverapi.bungee.payload.channel;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.netty.buffer.Unpooled;
import java.util.List;
import net.labymod.serverapi.api.LabyService;
import net.labymod.serverapi.api.extension.AddonExtension;
import net.labymod.serverapi.api.extension.ModificationExtension;
import net.labymod.serverapi.api.payload.PayloadBuffer;
import net.labymod.serverapi.api.protocol.ChunkCachingProtocol;
import net.labymod.serverapi.api.protocol.ShadowProtocol;
import net.labymod.serverapi.bungee.BungeeLabyModPlugin;
import net.labymod.serverapi.bungee.event.BungeeLabyModPlayerLoginEvent;
import net.labymod.serverapi.bungee.event.BungeeMessageReceiveEvent;
import net.labymod.serverapi.bungee.event.BungeeReceivePayloadEvent;
import net.labymod.serverapi.common.payload.DefaultLegacyLabyModPayloadChannel;
import net.labymod.serverapi.common.payload.DefaultPayloadBufferFactory;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class BungeeLegacyLabyModPayloadChannel extends DefaultLegacyLabyModPayloadChannel
    implements Listener {

  private static final JsonParser JSON_PARSER = new JsonParser();
  private final BungeeLabyModPlugin plugin;
  private final PayloadBuffer.Factory payloadBufferFactory;

  public BungeeLegacyLabyModPayloadChannel(LabyService service, BungeeLabyModPlugin plugin) {
    super(service);
    this.plugin = plugin;
    this.payloadBufferFactory = new DefaultPayloadBufferFactory();
  }

  @EventHandler
  public void receiveLabyMod3Payload(BungeeReceivePayloadEvent event) {
    if (!this.isLabyMod3MainChannel(event.getIdentifier())) {
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

    ProxiedPlayer bungeePlayer = (ProxiedPlayer) player;

    JsonObject object = new JsonObject();
    object.addProperty("version", this.plugin.getPluginVersion());
    this.service
        .getPayloadCommunicator()
        .sendLabyModMessage(bungeePlayer.getUniqueId(), "server_api", object);

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
