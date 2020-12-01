package net.labymod.serverapi.common.extension;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.labymod.serverapi.api.extension.ExtensionCollector;
import net.labymod.serverapi.api.extension.ModificationExtension;
import net.labymod.serverapi.api.extension.ModificationExtension.Factory;

/**
 * Default implementation of the {@link ExtensionCollector} to collect {@link
 * ModificationExtension}'s.
 */
public class DefaultModificationExtensionCollector
    implements ExtensionCollector<ModificationExtension> {

  private final ModificationExtension.Factory modificationExtensionFactory;

  public DefaultModificationExtensionCollector(Factory modificationExtensionFactory) {
    this.modificationExtensionFactory = modificationExtensionFactory;
  }

  /** {@inheritDoc} */
  @Override
  public List<ModificationExtension> collect(JsonObject object) {
    if (object.has("mods") || !object.get("mods").isJsonArray()) {
      return Collections.emptyList();
    }

    List<ModificationExtension> modificationExtensions = new ArrayList<>();

    for (JsonElement element : object.get("mods").getAsJsonArray()) {
      if (!element.isJsonObject()) {
        continue;
      }

      JsonObject addonObject = element.getAsJsonObject();

      if (this.shouldString(addonObject, "hash") || this.shouldString(addonObject, "name")) {
        continue;
      }

      modificationExtensions.add(
          this.modificationExtensionFactory.create(
              object.get("name").getAsString(), object.get("hash").getAsString()));
    }

    return modificationExtensions;
  }

  private boolean shouldString(JsonObject object, String name) {
    return !object.has(name)
        || !object.get(name).isJsonPrimitive()
        || !object.get(name).getAsJsonPrimitive().isString();
  }
}
