package net.labymod.serverapi.common.extension.addon;

import java.util.UUID;
import net.labymod.serverapi.api.extension.addon.RecommendedAddon;

public class DefaultRecommendAddonFactory implements RecommendedAddon.Factory {

  /** {@inheritDoc} */
  @Override
  public RecommendedAddon create(UUID publishedUniqueId, boolean required) {
    return new DefaultRecommendedAddon(publishedUniqueId, required);
  }
}
