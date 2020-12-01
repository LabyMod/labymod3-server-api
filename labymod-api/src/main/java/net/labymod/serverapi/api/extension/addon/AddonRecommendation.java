package net.labymod.serverapi.api.extension.addon;

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
 */
public interface AddonRecommendation {

  /**
   * Adds a recommending addon to a list which will be sent to the client.
   *
   * @param recommendedAddon The recommended addon to be added.
   * @return This object for a fluent chaining.
   */
  AddonRecommendation addRecommendAddon(RecommendedAddon recommendedAddon);

  /**
   * Adds to a list an array of recommending addons which will be sent to the client.
   *
   * @param recommendedAddons An array of recommended addons to be added.
   * @return This object for a fluent chaining.
   */
  AddonRecommendation addRecommendAddons(RecommendedAddon... recommendedAddons);

  /**
   * Sends the addon recommendation packet to the client.
   *
   * @param uniqueId The unique identifier of the receiver.
   */
  void sendRecommendAddons(UUID uniqueId);
}
