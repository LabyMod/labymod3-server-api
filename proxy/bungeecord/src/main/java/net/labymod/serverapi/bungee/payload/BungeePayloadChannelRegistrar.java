package net.labymod.serverapi.bungee.payload;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.labymod.serverapi.api.payload.PayloadChannelRegistrar;
import net.labymod.serverapi.api.payload.PayloadChannelType;
import net.labymod.serverapi.bungee.BungeeLabyModPlugin;
import net.md_5.bungee.api.ProxyServer;

@Singleton
public class BungeePayloadChannelRegistrar implements PayloadChannelRegistrar<String> {

  private final Multimap<PayloadChannelType, String> channelIdentifiers;
  private final BungeeLabyModPlugin plugin;

  @Inject
  private BungeePayloadChannelRegistrar(BungeeLabyModPlugin plugin) {
    this.plugin = plugin;
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

    for (String identifier : this.channelIdentifiers.get(PayloadChannelType.MODERN)) {

      if (identifier.equals(channelIdentifier)) {
        return;
      }
    }

    this.plugin.getProxy().registerChannel(channelIdentifier);
    this.channelIdentifiers.put(PayloadChannelType.LEGACY, channelIdentifier);
  }

  /** {@inheritDoc} */
  @Override
  public void registerModernChannelIdentifier(String namespace, String path) {
    String channelIdentifier = namespace + ":" + path;
    channelIdentifier = channelIdentifier.toLowerCase();

    for (String identifier : this.channelIdentifiers.get(PayloadChannelType.MODERN)) {

      if (identifier.equals(namespace + ":" + path)) {
        return;
      }
    }

    this.plugin.getProxy().registerChannel(channelIdentifier);
    this.channelIdentifiers.put(PayloadChannelType.MODERN, channelIdentifier);
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
    namespace = namespace.toLowerCase();
    path = path.toLowerCase();

    this.unregisterChannelIdentifier(namespace + ":" + path, PayloadChannelType.MODERN);
  }

  /** {@inheritDoc} */
  @Override
  public Multimap<PayloadChannelType, String> getChannelIdentifiers() {
    return this.channelIdentifiers;
  }

  private void unregisterChannelIdentifier(
      String channelIdentifier, PayloadChannelType payloadChannelType) {
    String toRemove = null;
    for (String identifier : this.channelIdentifiers.get(payloadChannelType)) {
      if (identifier.equals(channelIdentifier)) {
        toRemove = identifier;
        this.plugin.getProxy().unregisterChannel(identifier);
      }
    }

    if (toRemove == null) {
      return;
    }

    this.channelIdentifiers.remove(payloadChannelType, toRemove);
  }
}
