package net.labymod.serverapi.velocity.guice;

import com.google.inject.TypeLiteral;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import net.labymod.serverapi.api.payload.PayloadChannelRegistrar;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.common.connection.ConnectionService;
import net.labymod.serverapi.common.guice.LabyModAbstractModule;
import net.labymod.serverapi.common.payload.DefaultLegacyLabyModPayloadChannel;
import net.labymod.serverapi.velocity.connection.VelocityConnectionService;
import net.labymod.serverapi.velocity.payload.VelocityPayloadChannelRegistrar;
import net.labymod.serverapi.velocity.payload.VelocityPayloadCommunicator;
import net.labymod.serverapi.velocity.payload.channel.VelocityLegacyLabyModPayloadChannel;
import net.labymod.serverapi.velocity.player.VelocityLabyModPlayerFactory;
import net.labymod.serverapi.velocity.player.VelocityLabyModPlayerService;

public class LabyModVelocityModule extends LabyModAbstractModule {

  @Override
  protected void configure() {
    this.bind(PayloadCommunicator.class, VelocityPayloadCommunicator.class);
    this.bind(DefaultLegacyLabyModPayloadChannel.class, VelocityLegacyLabyModPayloadChannel.class);
    this.bind(new TypeLiteral<ConnectionService<Player>>() {}, VelocityConnectionService.class);
    this.bind(
        new TypeLiteral<LabyModPlayerService<Player>>() {}, VelocityLabyModPlayerService.class);
    this.bind(
        new TypeLiteral<LabyModPlayer.Factory<Player>>() {}, VelocityLabyModPlayerFactory.class);
    this.bind(
        new TypeLiteral<PayloadChannelRegistrar<ChannelIdentifier>>() {},
        VelocityPayloadChannelRegistrar.class);
  }
}
