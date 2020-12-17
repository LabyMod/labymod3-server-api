package net.labymod.serverapi.velocity.payload;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import com.velocitypowered.api.proxy.messages.ChannelRegistrar;
import com.velocitypowered.api.proxy.messages.LegacyChannelIdentifier;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import net.labymod.serverapi.api.payload.PayloadChannelRegistrar;
import net.labymod.serverapi.api.payload.PayloadChannelType;

/** The Velocity implementation of the {@link PayloadChannelRegistrar}. */
@Singleton
public class VelocityPayloadChannelRegistrar implements PayloadChannelRegistrar<ChannelIdentifier> {

  private final ChannelRegistrar channelRegistrar;
  private final Multimap<PayloadChannelType, ChannelIdentifier> channelIdentifiers;

  @Inject
  private VelocityPayloadChannelRegistrar(ProxyServer proxyServer) {
    this.channelRegistrar = proxyServer.getChannelRegistrar();
    this.channelIdentifiers = HashMultimap.create();
  }

  /** {@inheritDoc} */
  @Override
  public void registerModernLegacyChannelIdentifier(String channelIdentifier) {
    this.registerLegacyChannelIdentifier(channelIdentifier);
    this.registerModernChannelIdentifier("legacy", channelIdentifier);
  }

  /** {@inheritDoc} */
  @Override
  public void registerLegacyChannelIdentifier(String channelIdentifier) {
    for (ChannelIdentifier identifier : this.channelIdentifiers.get(PayloadChannelType.LEGACY)) {
      if (identifier.getId().equals(channelIdentifier)) return;
    }

    LegacyChannelIdentifier legacyChannelIdentifier =
        new LegacyChannelIdentifier(channelIdentifier);

    this.channelIdentifiers.put(PayloadChannelType.LEGACY, legacyChannelIdentifier);
    this.channelRegistrar.register(legacyChannelIdentifier);
  }

  /** {@inheritDoc} */
  @Override
  public void registerModernChannelIdentifier(String namespace, String path) {
    namespace = namespace.toLowerCase();
    path = path.toLowerCase();

    for (ChannelIdentifier channelIdentifier :
        this.channelIdentifiers.get(PayloadChannelType.MODERN)) {

      if (channelIdentifier.getId().equals(namespace + ":" + path)) {
        return;
      }
    }

    MinecraftChannelIdentifier minecraftChannelIdentifier =
        MinecraftChannelIdentifier.create(namespace, path);

    this.channelIdentifiers.put(PayloadChannelType.MODERN, minecraftChannelIdentifier);
    this.channelRegistrar.register(minecraftChannelIdentifier);
  }

  /** {@inheritDoc} */
  @Override
  public void unregisterModernLegacyChannelIdentifier(String channelIdentifier) {
    this.unregisterLegacyChannelIdentifier(channelIdentifier);
    this.unregisterModernChannelIdentifier("legacy", channelIdentifier);
  }

  /** {@inheritDoc} */
  @Override
  public void unregisterLegacyChannelIdentifier(String channelIdentifier) {
    this.unregisterChannelIdentifier(channelIdentifier, PayloadChannelType.LEGACY);
  }

  /** {@inheritDoc} */
  @Override
  public void unregisterModernChannelIdentifier(String namespace, String path) {
    this.unregisterChannelIdentifier(
        namespace.toLowerCase() + ":" + path.toLowerCase(), PayloadChannelType.MODERN);
  }

  /** {@inheritDoc} */
  @Override
  public Multimap<PayloadChannelType, ChannelIdentifier> getChannelIdentifiers() {
    return this.channelIdentifiers;
  }

  private void unregisterChannelIdentifier(
      String channelIdentifier, PayloadChannelType payloadChannelType) {
    ChannelIdentifier toRemove = null;
    for (ChannelIdentifier identifier : this.channelIdentifiers.get(payloadChannelType)) {
      if (identifier.getId().equals(channelIdentifier)) {
        toRemove = identifier;
        this.channelRegistrar.unregister(identifier);
      }
    }

    if (toRemove == null) {
      return;
    }

    this.channelIdentifiers.remove(payloadChannelType, toRemove);
  }
}
