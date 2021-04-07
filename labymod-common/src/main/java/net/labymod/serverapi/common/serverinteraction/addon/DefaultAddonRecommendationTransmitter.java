package net.labymod.serverapi.common.serverinteraction.addon;

import com.google.gson.JsonArray;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import net.labymod.serverapi.api.LabyService;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.serverinteraction.addon.AddonRecommendationTransmitter;
import net.labymod.serverapi.api.serverinteraction.addon.RecommendedAddon;

public class DefaultAddonRecommendationTransmitter implements AddonRecommendationTransmitter {

  private static final String ADDON_RECOMMENDATION_CHANNEL = "addon_recommendation";
  private final PayloadCommunicator payloadCommunicator;
  private final List<RecommendedAddon> recommendedAddons;

  public DefaultAddonRecommendationTransmitter(LabyService service) {
    this.payloadCommunicator = service.getPayloadCommunicator();
    this.recommendedAddons = new ArrayList<>();
  }

  /** {@inheritDoc} */
  @Override
  public AddonRecommendationTransmitter addRecommendAddon(RecommendedAddon recommendedAddon) {
    this.recommendedAddons.add(recommendedAddon);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public AddonRecommendationTransmitter addRecommendAddons(RecommendedAddon... recommendedAddons) {
    this.recommendedAddons.addAll(Arrays.asList(recommendedAddons));
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public void transmit(UUID uniqueId) {

    JsonArray recommendAddons = new JsonArray();

    for (RecommendedAddon addonRecommendation : this.recommendedAddons) {
      recommendAddons.add(addonRecommendation.asJsonObject());
    }

    this.recommendedAddons.clear();

    this.payloadCommunicator.sendLabyModMessage(
        uniqueId, ADDON_RECOMMENDATION_CHANNEL, recommendAddons);
  }
}
