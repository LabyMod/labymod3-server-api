package net.labymod.serverapi.bukkit.payload;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.labymod.serverapi.api.payload.PayloadChannelRegistrar;
import net.labymod.serverapi.api.payload.PayloadChannelType;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.bukkit.BukkitLabyModPlugin;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.plugin.messaging.PluginMessageListener;

@Singleton
public class BukkitPayloadChannelRegistrar implements PayloadChannelRegistrar<String> {

  private final BukkitLabyModPlugin plugin;
  private final Messenger messenger;
  private final Multimap<PayloadChannelType, String> channelIdentifiers;

  private final PayloadCommunicator payloadCommunicator;

  @Inject
  private BukkitPayloadChannelRegistrar(
      BukkitLabyModPlugin plugin, PayloadCommunicator payloadCommunicator) {
    this.plugin = plugin;
    this.messenger = this.plugin.getServer().getMessenger();
    this.payloadCommunicator = payloadCommunicator;
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

    for (String identifier : this.channelIdentifiers.get(PayloadChannelType.LEGACY)) {
      if (identifier.equals(channelIdentifier)) {
        return;
      }
    }

    this.channelIdentifiers.put(PayloadChannelType.LEGACY, channelIdentifier);
    this.messenger.registerOutgoingPluginChannel(this.plugin, channelIdentifier);
    this.messenger.registerIncomingPluginChannel(
        this.plugin, channelIdentifier, (PluginMessageListener) this.payloadCommunicator);
  }

  /** {@inheritDoc} */
  @Override
  public void registerModernChannelIdentifier(String namespace, String path) {
    String channelIdentifier = namespace + ":" + path;
    channelIdentifier = channelIdentifier.toLowerCase();

    for (String identifier : this.channelIdentifiers.get(PayloadChannelType.MODERN)) {
      if (identifier.equals(channelIdentifier)) {
        return;
      }
    }

    this.channelIdentifiers.put(PayloadChannelType.MODERN, channelIdentifier);
    this.messenger.registerOutgoingPluginChannel(this.plugin, channelIdentifier);
    this.messenger.registerIncomingPluginChannel(
        this.plugin, channelIdentifier, (PluginMessageListener) this.payloadCommunicator);
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
    String channelIdentifier = namespace + ":" + path;
    channelIdentifier = channelIdentifier.toLowerCase();

    this.unregisterChannelIdentifier(channelIdentifier, PayloadChannelType.MODERN);
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
        this.messenger.unregisterIncomingPluginChannel(this.plugin, identifier);
        this.messenger.unregisterOutgoingPluginChannel(this.plugin, identifier);
      }
    }

    if (toRemove == null) {
      return;
    }

    this.channelIdentifiers.remove(payloadChannelType, toRemove);
  }
}
