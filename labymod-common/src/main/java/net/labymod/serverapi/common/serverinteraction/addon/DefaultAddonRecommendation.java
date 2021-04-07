package net.labymod.serverapi.common.serverinteraction.addon;

import com.google.gson.JsonArray;
import net.labymod.serverapi.api.LabyService;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.serverinteraction.addon.AddonRecommendation;
import net.labymod.serverapi.api.serverinteraction.addon.RecommendedAddon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class DefaultAddonRecommendation implements AddonRecommendation {

  private static final String ADDON_RECOMMENDATION_CHANNEL = "addon_recommendation";
  private final PayloadCommunicator payloadCommunicator;
  private final List<RecommendedAddon> recommendedAddons;

  public DefaultAddonRecommendation(LabyService service) {
    this.payloadCommunicator = service.getPayloadCommunicator();
    this.recommendedAddons = new ArrayList<>();
  }

  /** {@inheritDoc} */
  @Override
  public AddonRecommendation addRecommendAddon(RecommendedAddon recommendedAddon) {
    this.recommendedAddons.add(recommendedAddon);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public AddonRecommendation addRecommendAddons(RecommendedAddon... recommendedAddons) {
    this.recommendedAddons.addAll(Arrays.asList(recommendedAddons));
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public void sendRecommendAddons(UUID uniqueId) {
    JsonArray recommendAddons = new JsonArray();

    for (RecommendedAddon addonRecommendation : this.recommendedAddons) {
      recommendAddons.add(addonRecommendation.asJsonObject());
    }

    this.recommendedAddons.clear();

    this.payloadCommunicator.sendLabyModMessage(
        uniqueId, ADDON_RECOMMENDATION_CHANNEL, recommendAddons);
  }
}
