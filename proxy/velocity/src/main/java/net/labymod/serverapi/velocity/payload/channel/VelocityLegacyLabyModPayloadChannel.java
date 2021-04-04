package net.labymod.serverapi.velocity.payload.channel;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import io.netty.buffer.Unpooled;
import java.util.List;
import java.util.Optional;
import net.labymod.serverapi.api.LabyService;
import net.labymod.serverapi.api.extension.AddonExtension;
import net.labymod.serverapi.api.extension.ModificationExtension;
import net.labymod.serverapi.api.payload.PayloadBuffer;
import net.labymod.serverapi.api.protocol.ChunkCachingProtocol;
import net.labymod.serverapi.api.protocol.ShadowProtocol;
import net.labymod.serverapi.common.payload.DefaultLegacyLabyModPayloadChannel;
import net.labymod.serverapi.common.payload.DefaultPayloadBufferFactory;
import net.labymod.serverapi.velocity.VelocityLabyModPlugin;
import net.labymod.serverapi.velocity.event.VelocityLabyModPlayerLoginEvent;
import net.labymod.serverapi.velocity.event.VelocityMessageReceiveEvent;
import net.labymod.serverapi.velocity.event.VelocityReceivePayloadEvent;

public class VelocityLegacyLabyModPayloadChannel extends DefaultLegacyLabyModPayloadChannel {

  private final VelocityLabyModPlugin plugin;
  private final ProxyServer proxyServer;
  private final PayloadBuffer.Factory payloadBufferFactory;

  public VelocityLegacyLabyModPayloadChannel(
      VelocityLabyModPlugin plugin, ProxyServer proxyServer, LabyService service) {
    super(service);
    this.plugin = plugin;
    this.proxyServer = proxyServer;
    this.payloadBufferFactory = new DefaultPayloadBufferFactory();
  }

  @Subscribe
  public void legacyLabyMod(VelocityReceivePayloadEvent event) {
    if (!this.isLabyMod3MainChannel(event.getIdentifier())) {
      return;
    }

    Optional<Player> optionalPlayer = this.proxyServer.getPlayer(event.getUniqueId());

    if (!optionalPlayer.isPresent()) {
      return;
    }

    Player player = optionalPlayer.get();

    this.readPayload(
        player, this.payloadBufferFactory.create(Unpooled.wrappedBuffer(event.getPayload())));
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

    Player velocityPlayer = (Player) player;

    JsonObject object = new JsonObject();
    object.addProperty("version", this.plugin.getPluginVersion());
    this.service
        .getPayloadCommunicator()
        .sendLabyModMessage(velocityPlayer.getUniqueId(), "server_api", object);

    this.proxyServer
        .getEventManager()
        .fire(
            new VelocityLabyModPlayerLoginEvent(
                velocityPlayer,
                addons,
                modifications,
                version,
                chunkCachingProtocol,
                shadowProtocol));
  }
}
