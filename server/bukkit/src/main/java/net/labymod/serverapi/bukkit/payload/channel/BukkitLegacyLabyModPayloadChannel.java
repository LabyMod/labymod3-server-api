package net.labymod.serverapi.bukkit.payload.channel;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.netty.buffer.Unpooled;
import java.util.List;
import net.labymod.serverapi.api.LabyService;
import net.labymod.serverapi.api.extension.AddonExtension;
import net.labymod.serverapi.api.extension.ModificationExtension;
import net.labymod.serverapi.api.payload.PayloadBuffer;
import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.api.protocol.ChunkCachingProtocol;
import net.labymod.serverapi.api.protocol.ShadowProtocol;
import net.labymod.serverapi.bukkit.BukkitLabyModPlugin;
import net.labymod.serverapi.bukkit.event.BukkitLabyModPlayerLoginEvent;
import net.labymod.serverapi.bukkit.event.BukkitMessageReceiveEvent;
import net.labymod.serverapi.bukkit.event.BukkitReceivePayloadEvent;
import net.labymod.serverapi.common.payload.DefaultLegacyLabyModPayloadChannel;
import net.labymod.serverapi.common.payload.DefaultPayloadBufferFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BukkitLegacyLabyModPayloadChannel extends DefaultLegacyLabyModPayloadChannel
    implements Listener {

  private static final JsonParser JSON_PARSER = new JsonParser();

  private final BukkitLabyModPlugin plugin;
  private final LabyModPlayerService<Player> labyModPlayerService;
  private final PayloadBuffer.Factory payloadBufferFactory;

  public BukkitLegacyLabyModPayloadChannel(BukkitLabyModPlugin plugin, LabyService service) {
    super(service);
    this.plugin = plugin;
    this.labyModPlayerService = service.getLabyPlayerService();
    this.payloadBufferFactory = new DefaultPayloadBufferFactory();
  }

  @EventHandler
  public void receiveLabyMod3Payload(BukkitReceivePayloadEvent event) {
    Player player = this.plugin.getServer().getPlayer(event.getUniqueId());

    if (player == null) {
      return;
    }

    this.readPayload(
        player, this.payloadBufferFactory.create(Unpooled.wrappedBuffer(event.getPayload())));
  }

  @Override
  public JsonElement parseMessageContent(String messageContent) {
    return JSON_PARSER.parse(messageContent);
  }

  @Override
  public <T> void onReceiveMessage(T player, String key, JsonElement content) {
    this.plugin
        .getServer()
        .getPluginManager()
        .callEvent(new BukkitMessageReceiveEvent((Player) player, key, content));
  }

  @Override
  public <T> void onLabyModPlayerJoin(
      T player,
      List<AddonExtension> addons,
      List<ModificationExtension> modifications,
      String version,
      ChunkCachingProtocol chunkCachingProtocol,
      ShadowProtocol shadowProtocol) {
    Player bukkitPlayer = (Player) player;

    JsonObject object = new JsonObject();
    object.addProperty("version", this.plugin.getPluginVersion());
    this.service
        .getPayloadCommunicator()
        .sendLabyModMessage(bukkitPlayer.getUniqueId(), "server_api", object);
    this.plugin
        .getServer()
        .getPluginManager()
        .callEvent(
            new BukkitLabyModPlayerLoginEvent(
                bukkitPlayer,
                addons,
                modifications,
                version,
                chunkCachingProtocol,
                shadowProtocol));
  }
}
