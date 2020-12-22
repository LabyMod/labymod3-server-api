package net.labymod.serverapi.bukkit.guice;

import com.comphenix.protocol.events.PacketContainer;
import com.google.inject.TypeLiteral;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import net.labymod.serverapi.api.connection.ConnectionService;
import net.labymod.serverapi.api.payload.PayloadChannelRegistrar;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.api.protocol.chunkcaching.ChunkCaching;
import net.labymod.serverapi.api.protocol.chunkcaching.legacy.ChunkCacheLegacyBulk;
import net.labymod.serverapi.api.protocol.chunkcaching.legacy.ChunkCacheLegacySingle;
import net.labymod.serverapi.api.protocol.chunkcaching.modern.ChunkCacheModern;
import net.labymod.serverapi.bukkit.connection.BukkitConnectionService;
import net.labymod.serverapi.bukkit.payload.BukkitPayloadChannelRegistrar;
import net.labymod.serverapi.bukkit.payload.BukkitPayloadCommunicator;
import net.labymod.serverapi.bukkit.payload.channel.BukkitLegacyLabyModPayloadChannel;
import net.labymod.serverapi.bukkit.player.BukkitLabyModPlayerFactory;
import net.labymod.serverapi.bukkit.player.BukkitLabyModPlayerService;
import net.labymod.serverapi.bukkit.protocol.chunkcaching.BukkitChunkCaching;
import net.labymod.serverapi.bukkit.protocol.chunkcaching.legacy.BukkitChunkCacheLegacyBulk;
import net.labymod.serverapi.bukkit.protocol.chunkcaching.legacy.BukkitChunkCacheLegacySingle;
import net.labymod.serverapi.bukkit.protocol.chunkcaching.modern.BukkitChunkCacheModern;
import net.labymod.serverapi.common.guice.LabyModAbstractModule;
import net.labymod.serverapi.common.payload.DefaultLegacyLabyModPayloadChannel;
import org.bukkit.entity.Player;

public class LabyModBukkitModule extends LabyModAbstractModule {

  @Override
  protected void configure() {
    this.bind(PayloadCommunicator.class, BukkitPayloadCommunicator.class);
    this.bind(DefaultLegacyLabyModPayloadChannel.class, BukkitLegacyLabyModPayloadChannel.class);
    this.bind(new TypeLiteral<ConnectionService<Player>>() {}, BukkitConnectionService.class);
    this.bind(new TypeLiteral<LabyModPlayerService<Player>>() {}, BukkitLabyModPlayerService.class);
    this.bind(
        new TypeLiteral<LabyModPlayer.Factory<Player>>() {}, BukkitLabyModPlayerFactory.class);
    this.bind(
        new TypeLiteral<PayloadChannelRegistrar<String>>() {}, BukkitPayloadChannelRegistrar.class);

    this.bind(new TypeLiteral<ChunkCaching<Player, PacketContainer>>() {})
        .to(BukkitChunkCaching.class);

    this.installFactory(
        ChunkCacheModern.class, BukkitChunkCacheModern.class, ChunkCacheModern.Factory.class);

    this.install(
        new FactoryModuleBuilder()
            .implement(
                new TypeLiteral<ChunkCacheLegacySingle<PacketContainer>>() {},
                BukkitChunkCacheLegacySingle.class)
            .build(new TypeLiteral<ChunkCacheLegacySingle.Factory<PacketContainer>>() {}));

    this.install(
        new FactoryModuleBuilder()
            .implement(
                new TypeLiteral<ChunkCacheLegacyBulk<PacketContainer>>() {},
                BukkitChunkCacheLegacyBulk.class)
            .build(new TypeLiteral<ChunkCacheLegacyBulk.Factory<PacketContainer>>() {}));
  }
}
