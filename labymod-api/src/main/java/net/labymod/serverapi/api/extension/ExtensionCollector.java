package net.labymod.serverapi.api.extension;

import com.google.gson.JsonObject;
import java.util.List;

/**
 * Represents a collector that collects all {@link Extension} as {@link List} from the specified
 * {@link JsonObject}.
 *
 * @param <T> The extension type to be collected.
 */
public interface ExtensionCollector<T extends Extension<?, ?>> {

  /**
   * Collects all {@link Extension} as {@link List} from the given {@link JsonObject}.
   *
   * @param object The json object, which contains all extensions.
   * @return A collection with all collected extensions.
   */
  List<T> collect(JsonObject object);
}
