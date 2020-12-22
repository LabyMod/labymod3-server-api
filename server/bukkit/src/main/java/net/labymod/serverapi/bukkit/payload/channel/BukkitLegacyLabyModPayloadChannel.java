package net.labymod.serverapi.bukkit.payload.channel;

import com.comphenix.protocol.events.PacketContainer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.netty.buffer.Unpooled;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;
import net.labymod.serverapi.api.extension.AddonExtension;
import net.labymod.serverapi.api.extension.ExtensionCollector;
import net.labymod.serverapi.api.extension.ModificationExtension;
import net.labymod.serverapi.api.payload.PayloadBuffer;
import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.api.protocol.ChunkCachingProtocol;
import net.labymod.serverapi.api.protocol.ChunkCachingProtocol.Factory;
import net.labymod.serverapi.api.protocol.ShadowProtocol;
import net.labymod.serverapi.api.protocol.chunkcaching.ChunkCaching;
import net.labymod.serverapi.api.protocol.chunkcaching.LabyModPlayerChunkCaching;
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
  private final ChunkCaching<Player, PacketContainer> chunkCaching;
  private final LabyModPlayerService<Player> labyModPlayerService;
  private final PayloadBuffer.Factory payloadBufferFactory;

  @Inject
  private BukkitLegacyLabyModPayloadChannel(
      ExtensionCollector<AddonExtension> addonExtensionCollector,
      ExtensionCollector<ModificationExtension> modificationExtensionCollector,
      Factory chunkCachingProtocolFactory,
      ShadowProtocol.Factory shadowProtocolFactory,
      BukkitLabyModPlugin plugin,
      ChunkCaching<Player, PacketContainer> chunkCaching,
      LabyModPlayerService<Player> labyModPlayerService,
      PayloadBuffer.Factory payloadBufferFactory) {
    super(
        addonExtensionCollector,
        modificationExtensionCollector,
        chunkCachingProtocolFactory,
        shadowProtocolFactory);
    this.plugin = plugin;
    this.chunkCaching = chunkCaching;
    this.labyModPlayerService = labyModPlayerService;
    this.payloadBufferFactory = payloadBufferFactory;
  }

  @EventHandler
  public void legacyLabyMod(BukkitReceivePayloadEvent event) {
    String identifier = event.getIdentifier();
    if (!this.isLegacyLMCChannel(identifier)) {

      if (isLegacyCCPChannel(identifier)) {
        this.handleChunkCachingProtocol(event.getUniqueId(), event.getPayload());
      }

      return;
    }

    Player player = this.plugin.getServer().getPlayer(event.getUniqueId());

    if (player == null) {
      return;
    }

    this.readPayload(
        player, this.payloadBufferFactory.create(Unpooled.wrappedBuffer(event.getPayload())));
  }

  private void handleChunkCachingProtocol(UUID uniqueId, byte[] data) {
    ByteBuffer byteBuffer = ByteBuffer.wrap(data);

    byte opcode = byteBuffer.get();

    if (opcode == 0x21) {

      LabyModPlayerChunkCaching<Player, PacketContainer> chunkCaching = this.chunkCaching.getChunkCache(uniqueId);
      if (chunkCaching == null) {
        return;
      }

      short length = byteBuffer.getShort();
      boolean[] mask = new boolean[length];
      int[][] coordinates = new int[length][2];

      for (int i = 0; i < coordinates.length; i++) {
        mask[i] = byteBuffer.get() == 1;
        coordinates[i][0] = byteBuffer.getInt();
        coordinates[i][1] = byteBuffer.getInt();
      }

      this.labyModPlayerService
          .getPlayer(uniqueId)
          .ifPresent(
              player -> {
                chunkCaching.request(player, mask, coordinates);
              });
    }
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
