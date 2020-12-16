package net.labymod.serverapi.bukkit.payload.channel;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.netty.buffer.Unpooled;
import java.util.List;
import net.labymod.serverapi.api.extension.AddonExtension;
import net.labymod.serverapi.api.extension.ModificationExtension;
import net.labymod.serverapi.api.protocol.ChunkCachingProtocol;
import net.labymod.serverapi.api.protocol.ShadowProtocol;
import net.labymod.serverapi.bukkit.BukkitLabyModPlugin;
import net.labymod.serverapi.bukkit.event.BukkitLabyModPlayerLoginEvent;
import net.labymod.serverapi.bukkit.event.BukkitMessageReceiveEvent;
import net.labymod.serverapi.bukkit.event.BukkitReceivePayloadEvent;
import net.labymod.serverapi.common.extension.DefaultAddonExtensionCollector;
import net.labymod.serverapi.common.extension.DefaultModificationExtensionCollector;
import net.labymod.serverapi.common.payload.DefaultLegacyLabyModPayloadChannel;
import net.labymod.serverapi.common.payload.DefaultPayloadBuffer;
import net.labymod.serverapi.common.protocol.DefaultChunkCachingProtocolFactory;
import net.labymod.serverapi.common.protocol.DefaultShadowProtocolFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BukkitLegacyLabyModPayloadChannel extends DefaultLegacyLabyModPayloadChannel
    implements Listener {

  private static final JsonParser JSON_PARSER = new JsonParser();

  private final BukkitLabyModPlugin plugin;

  public BukkitLegacyLabyModPayloadChannel(BukkitLabyModPlugin plugin) {
    super(
        DefaultAddonExtensionCollector.getInstance(),
        DefaultModificationExtensionCollector.getInstance(),
        DefaultChunkCachingProtocolFactory.getInstance(),
        DefaultShadowProtocolFactory.getInstance());
    this.plugin = plugin;
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

    this.readPayload(player, new DefaultPayloadBuffer(Unpooled.wrappedBuffer(event.getPayload())));
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
