package net.labymod.serverapi.bukkit.payload.channel;

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
import net.labymod.serverapi.bukkit.BukkitLabyModPlugin;
import net.labymod.serverapi.bukkit.event.BukkitLabyModPlayerLoginEvent;
import net.labymod.serverapi.bukkit.event.BukkitMessageReceiveEvent;
import net.labymod.serverapi.bukkit.event.BukkitReceivePayloadEvent;
import net.labymod.serverapi.common.payload.DefaultLegacyLabyModPayloadChannel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Singleton
public class BukkitLegacyLabyModPayloadChannel extends DefaultLegacyLabyModPayloadChannel
    implements Listener {

  private static final JsonParser JSON_PARSER = new JsonParser();

  private final BukkitLabyModPlugin plugin;
  private final PayloadBuffer.Factory payloadBufferFactory;

  @Inject
  private BukkitLegacyLabyModPayloadChannel(
      ExtensionCollector<AddonExtension> addonExtensionCollector,
      ExtensionCollector<ModificationExtension> modificationExtensionCollector,
      Factory chunkCachingProtocolFactory,
      ShadowProtocol.Factory shadowProtocolFactory,
      BukkitLabyModPlugin plugin,
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
  public void legacyLabyMod(BukkitReceivePayloadEvent event) {
    if (!this.isLegacyChannel(event.getIdentifier())) {
      return;
    }

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
    this.plugin
        .getServer()
        .getPluginManager()
        .callEvent(
            new BukkitLabyModPlayerLoginEvent(
                (Player) player,
                addons,
                modifications,
                version,
                chunkCachingProtocol,
                shadowProtocol));
  }
}
