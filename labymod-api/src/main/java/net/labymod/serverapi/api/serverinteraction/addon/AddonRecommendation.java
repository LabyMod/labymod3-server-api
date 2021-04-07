package net.labymod.serverapi.api.serverinteraction.addon;

import java.util.UUID;

/**
 * Represents the addon recommendation packet.
 *
 * <p><b>Overview:</b>
 *
 * <p>With this packet the server can recommend <b>published</b> addons to the user and the user can
 * install them very simple in a GUI. Multiple add-ons can be suggested at the same time or can also
 * be flagged as being required. You have to react to the response of the client if an addon is
 * required.
 * @deprecated Use {@link AddonRecommendationTransmitter}
 */
@Deprecated
public interface AddonRecommendation {

  /**
   * Adds a recommending addon to a list which will be sent to the client.
   *
   * @param recommendedAddon The recommended addon to be added.
   * @return This object for a fluent chaining.
   * @deprecated Use {@link AddonRecommendationTransmitter#addRecommendAddon(RecommendedAddon)}
   */
  @Deprecated
  AddonRecommendation addRecommendAddon(RecommendedAddon recommendedAddon);

  /**
   * Adds to a list an array of recommending addons which will be sent to the client.
   *
   * @param recommendedAddons An array of recommended addons to be added.
   * @return This object for a fluent chaining.
   * @deprecated Use {@link AddonRecommendationTransmitter#addRecommendAddons(RecommendedAddon...)}
   */
  @Deprecated
  AddonRecommendation addRecommendAddons(RecommendedAddon... recommendedAddons);

  /**
   * Sends the addon recommendation packet to the client.
   *
   * @param uniqueId The unique identifier of the receiver.
   * @deprecated Use {@link AddonRecommendationTransmitter#transmit(UUID)}
   */
  @Deprecated
  void sendRecommendAddons(UUID uniqueId);
}
