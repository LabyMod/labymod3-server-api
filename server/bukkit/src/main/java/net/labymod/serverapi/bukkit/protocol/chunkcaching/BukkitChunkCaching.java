package net.labymod.serverapi.bukkit.protocol.chunkcaching;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import java.util.Map;
import java.util.UUID;
import net.labymod.serverapi.api.payload.PayloadChannelRegistrar;
import net.labymod.serverapi.api.protocol.chunkcaching.ChunkCaching;
import net.labymod.serverapi.api.protocol.chunkcaching.LabyModPlayerChunkCaching;
import net.labymod.serverapi.common.guice.LabyModInjector;
import org.bukkit.entity.Player;

@Singleton
public class BukkitChunkCaching implements ChunkCaching<Player> {

  private final Map<UUID, LabyModPlayerChunkCaching<Player>> cache;

  @Inject
  public BukkitChunkCaching() {
    this.cache = Maps.newConcurrentMap();
    PayloadChannelRegistrar<String> payloadChannelRegistrar =
        LabyModInjector.getInstance()
            .getInjectedInstance(new TypeLiteral<PayloadChannelRegistrar<String>>() {});
    payloadChannelRegistrar.registerModernLegacyChannelIdentifier("CCP");
  }

  @Override
  public LabyModPlayerChunkCaching<Player> getChunkCache(UUID uniqueId) {
    return this.cache.get(uniqueId);
  }

  @Override
  public Map<UUID, LabyModPlayerChunkCaching<Player>> getCache() {
    return this.cache;
  }
}
