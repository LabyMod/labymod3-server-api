package net.labymod.serverapi.common.serverinteraction.addon;

import net.labymod.serverapi.api.serverinteraction.addon.RecommendedAddon;
import java.util.UUID;

public class DefaultRecommendedAddonFactory implements RecommendedAddon.Factory {

  @Override
  public RecommendedAddon create(UUID publishedUniqueId, boolean required) {
    return new DefaultRecommendedAddon(publishedUniqueId, required);
  }
}
