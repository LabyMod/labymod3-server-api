package net.labymod.serverapi.bukkit.protocol.chunkcaching;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.google.common.collect.Maps;
import com.google.inject.ConfigurationException;
import com.google.inject.Inject;
import com.google.inject.ProvisionException;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import net.labymod.serverapi.api.payload.PayloadChannelRegistrar;
import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.api.protocol.chunkcaching.ChunkCaching;
import net.labymod.serverapi.api.protocol.chunkcaching.ChunkHandle;
import net.labymod.serverapi.api.protocol.chunkcaching.LabyModPlayerChunkCaching;
import net.labymod.serverapi.bukkit.BukkitLabyModPlugin;
import net.labymod.serverapi.common.guice.LabyModInjector;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Singleton
public class BukkitChunkCaching implements ChunkCaching<Player, PacketContainer> {

  private final Map<UUID, LabyModPlayerChunkCaching<Player, PacketContainer>> cache;

  @Inject
  public BukkitChunkCaching(
      BukkitLabyModPlugin plugin,
      PayloadChannelRegistrar<String> payloadChannelRegistrar,
      ChunkCaching<Player, PacketContainer> caching,
      LabyModPlayerService<Player> labyModPlayerService) {
    this.cache = Maps.newConcurrentMap();

    Bukkit.getScheduler()
        .runTaskTimerAsynchronously(
            plugin,
            () -> {
              long milliseconds = System.currentTimeMillis() - TimeUnit.SECONDS.toMillis(10);
              this.cache.forEach((uuid, chunkCaching) -> chunkCaching.clearOlder(milliseconds));
            },
            100L,
            100L);

    try {
      ChunkHandle<Player, PacketContainer, PacketType> chunkHandle =
          LabyModInjector.getInstance()
              .getInjectedInstance(
                  new TypeLiteral<ChunkHandle<Player, PacketContainer, PacketType>>() {});

      ProtocolLibrary.getProtocolManager()
          .addPacketListener(
              new PacketAdapter(
                  plugin,
                  ListenerPriority.HIGHEST,
                  Server.MAP_CHUNK,
                  Server.MAP_CHUNK_BULK,
                  Server.UPDATE_SIGN) {
                @Override
                public void onPacketSending(PacketEvent event) {
                  labyModPlayerService
                      .getPlayer(event.getPlayer().getUniqueId())
                      .ifPresent(
                          player -> {
                            LabyModPlayerChunkCaching<Player, PacketContainer> chunkCache =
                                caching.getChunkCache(player.getUniqueId());

                            if (chunkCache == null) {
                              return;
                            }

                            if (chunkHandle.handle(
                                player, chunkCache, event.getPacketType(), event.getPacket())) {
                              event.setCancelled(true);
                            }
                          });
                }
              });

    } catch (ProvisionException | ConfigurationException ignored) {

    }

    payloadChannelRegistrar.registerModernLegacyChannelIdentifier("CCP");
  }

  /** {@inheritDoc} */
  @Override
  public LabyModPlayerChunkCaching<Player, PacketContainer> getChunkCache(UUID uniqueId) {
    return this.cache.get(uniqueId);
  }

  /** {@inheritDoc} */
  @Override
  public Map<UUID, LabyModPlayerChunkCaching<Player, PacketContainer>> getCache() {
    return this.cache;
  }
}
