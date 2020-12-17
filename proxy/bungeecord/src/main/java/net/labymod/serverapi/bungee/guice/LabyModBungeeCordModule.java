package net.labymod.serverapi.bungee.guice;

import com.google.inject.TypeLiteral;
import net.labymod.serverapi.api.payload.PayloadChannelRegistrar;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.bungee.connection.BungeeConnectionService;
import net.labymod.serverapi.bungee.payload.BungeePayloadChannelRegistrar;
import net.labymod.serverapi.bungee.payload.BungeePayloadCommunicator;
import net.labymod.serverapi.bungee.payload.channel.BungeeLegacyLabyModPayloadChannel;
import net.labymod.serverapi.bungee.player.BungeeLabyModPlayerFactory;
import net.labymod.serverapi.bungee.player.BungeeLabyModPlayerService;
import net.labymod.serverapi.common.connection.ConnectionService;
import net.labymod.serverapi.common.guice.LabyModAbstractModule;
import net.labymod.serverapi.common.payload.DefaultLegacyLabyModPayloadChannel;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class LabyModBungeeCordModule extends LabyModAbstractModule {

  @Override
  protected void configure() {
    this.bind(PayloadCommunicator.class, BungeePayloadCommunicator.class);
    this.bind(DefaultLegacyLabyModPayloadChannel.class, BungeeLegacyLabyModPayloadChannel.class);
    this.bind(
        new TypeLiteral<ConnectionService<ProxiedPlayer>>() {}, BungeeConnectionService.class);
    this.bind(
        new TypeLiteral<LabyModPlayerService<ProxiedPlayer>>() {},
        BungeeLabyModPlayerService.class);
    this.bind(
        new TypeLiteral<LabyModPlayer.Factory<ProxiedPlayer>>() {},
        BungeeLabyModPlayerFactory.class);
    this.bind(
        new TypeLiteral<PayloadChannelRegistrar<String>>() {}, BungeePayloadChannelRegistrar.class);
  }
}
