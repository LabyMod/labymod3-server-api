package net.labymod.serverapi.common.extension.addon;

import com.google.gson.JsonObject;
import java.util.UUID;
import net.labymod.serverapi.api.extension.addon.RecommendedAddon;

public class DefaultRecommendedAddon implements RecommendedAddon {

  private final UUID publishedUniqueId;
  private final JsonObject recommendAddonObject;
  private boolean required;

  public DefaultRecommendedAddon(UUID publishedUniqueId, boolean required) {
    this.publishedUniqueId = publishedUniqueId;
    this.recommendAddonObject = new JsonObject();
    this.required = required;
    this.recommendAddonObject.addProperty("uuid", publishedUniqueId.toString());
  }

  /** {@inheritDoc} */
  @Override
  public UUID getPublishedUniqueId() {
    return this.publishedUniqueId;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isRequired() {
    return this.required;
  }

  /** {@inheritDoc} */
  @Override
  public void setRequired(boolean required) {
    this.required = required;
    this.recommendAddonObject.addProperty("required", required);
  }

  /** {@inheritDoc} */
  @Override
  public JsonObject asJsonObject() {
    return this.recommendAddonObject;
  }
}
