package net.labymod.serverapi.bukkit.guice;

import com.google.inject.TypeLiteral;
import net.labymod.serverapi.api.payload.PayloadChannelRegistrar;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.bukkit.connection.BukkitConnectionService;
import net.labymod.serverapi.bukkit.payload.BukkitPayloadChannelRegistrar;
import net.labymod.serverapi.bukkit.payload.BukkitPayloadCommunicator;
import net.labymod.serverapi.bukkit.payload.channel.BukkitLegacyLabyModPayloadChannel;
import net.labymod.serverapi.bukkit.player.BukkitLabyModPlayerFactory;
import net.labymod.serverapi.bukkit.player.BukkitLabyModPlayerService;
import net.labymod.serverapi.api.connection.ConnectionService;
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
  }
}
