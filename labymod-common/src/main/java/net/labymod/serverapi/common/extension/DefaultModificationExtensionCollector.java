package net.labymod.serverapi.common.extension;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.labymod.serverapi.api.extension.ExtensionCollector;
import net.labymod.serverapi.api.extension.ModificationExtension;

/**
 * Default implementation of the {@link ExtensionCollector} to collect {@link
 * ModificationExtension}'s.
 */
@Singleton
public class DefaultModificationExtensionCollector
    implements ExtensionCollector<ModificationExtension> {

  private final ModificationExtension.Factory modificationExtensionFactory;

  @Inject
  private DefaultModificationExtensionCollector(
      ModificationExtension.Factory modificationExtensionFactory) {
    this.modificationExtensionFactory = modificationExtensionFactory;
  }

  /** {@inheritDoc} */
  @Override
  public List<ModificationExtension> collect(JsonObject object) {
    if (!object.has("mods") || !object.get("mods").isJsonArray()) {
      return Collections.emptyList();
    }

    List<ModificationExtension> modificationExtensions = new ArrayList<>();

    for (JsonElement element : object.get("mods").getAsJsonArray()) {
      if (!element.isJsonObject()) {
        continue;
      }

      JsonObject modificationObject = element.getAsJsonObject();

      if (this.shouldString(modificationObject, "hash")
          || this.shouldString(modificationObject, "name")) {
        continue;
      }

      modificationExtensions.add(
          this.modificationExtensionFactory.create(
              modificationObject.get("name").getAsString(),
              modificationObject.get("hash").getAsString()));
    }

    return modificationExtensions;
  }

  private boolean shouldString(JsonObject object, String name) {
    return !object.has(name)
        || !object.get(name).isJsonPrimitive()
        || !object.get(name).getAsJsonPrimitive().isString();
  }
}
