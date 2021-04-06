package net.labymod.serverapi.common.extension;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.labymod.serverapi.api.extension.AddonExtension;
import net.labymod.serverapi.api.extension.ExtensionCollector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


public class DefaultAddonExtensionCollector implements ExtensionCollector<AddonExtension> {

  private final AddonExtension.Factory addonExtensionFactory;


  public DefaultAddonExtensionCollector() {
    this.addonExtensionFactory = new DefaultAddonExtensionFactory();
  }

  /** {@inheritDoc} */
  @Override
  public List<AddonExtension> collect(JsonObject object) {
    if (!object.has("addons") || !object.get("addons").isJsonArray()) {
      return Collections.emptyList();
    }

    List<AddonExtension> addonExtensions = new ArrayList<>();

    for (JsonElement element : object.get("addons").getAsJsonArray()) {
      if (!element.isJsonObject()) {
        continue;
      }

      JsonObject addonObject = element.getAsJsonObject();

      if (this.shouldString(addonObject, "uuid") || this.shouldString(addonObject, "name")) {
        continue;
      }

      UUID uniqueId;

      try {
        uniqueId = UUID.fromString(addonObject.get("uuid").getAsString());
      } catch (IllegalArgumentException ignored) {
        continue;
      }

      addonExtensions.add(
          this.addonExtensionFactory.create(addonObject.get("name").getAsString(), uniqueId));
    }

    return addonExtensions;
  }

  private boolean shouldString(JsonObject object, String name) {
    return !object.has(name)
        || !object.get(name).isJsonPrimitive()
        || !object.get(name).getAsJsonPrimitive().isString();
  }
}
