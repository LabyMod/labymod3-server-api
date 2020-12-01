package net.labymod.serverapi.api.payload;

/** Represents a registrar to register and unregister payload channel's. */
public interface PayloadChannelRegistrar {

  /**
   * Registers a new modern and legacy payload channel.
   *
   * <p><b>Note:</b> The modern payload channel namespace is `legacy`.
   *
   * @param channelIdentifier The channel identifier for the modern and legacy payload channel.
   */
  void registerModernLegacyChannelIdentifier(String channelIdentifier);

  /**
   * Registers a new legacy payload channel with the {@code channelIdentifier}.
   *
   * @param channelIdentifier The channel identifier for the legacy payload channel.
   */
  void registerLegacyChannelIdentifier(String channelIdentifier);

  /**
   * Registers a new modern payload channel with the given {@code namespace} and {@code path}.
   *
   * @param namespace The namespace of the channel identifier.
   * @param path The path of the channel identifier.
   */
  void registerModernChannelIdentifier(String namespace, String path);

  /**
   * Unregisters a modern and legacy payload channel.
   *
   * @param channelIdentifier The channel identifier of the payload channels to be unregistered.
   */
  void unregisterModernLegacyChannelIdentifier(String channelIdentifier);

  /**
   * Unregisters a legacy payload channel.
   *
   * @param channelIdentifier The channel identifier to be unregistered.
   */
  void unregisterLegacyChannelIdentifier(String channelIdentifier);

  /**
   * Unregisters a modern payload channel.
   *
   * @param namespace The namespace of the modern payload channel to be unregistered.
   * @param path The path of the modern payload channel to be unregistered.
   */
  void unregisterModernChannelIdentifier(String namespace, String path);
}
