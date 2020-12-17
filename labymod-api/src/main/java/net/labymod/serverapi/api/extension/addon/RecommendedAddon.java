package net.labymod.serverapi.api.extension.addon;

import com.google.gson.JsonObject;
import com.google.inject.assistedinject.Assisted;
import java.util.UUID;

/** Represents a recommended addon. */
public interface RecommendedAddon {

  /**
   * Retrieves the published unique identifier of the recommended addon.
   *
   * @return The published unique identifier of the recommended addon.
   */
  UUID getPublishedUniqueId();

  /**
   * Whether the recommended addon is required.
   *
   * @return {@code true} if the recommended addon is required, otherwise {@code false}.
   */
  boolean isRequired();

  /**
   * Changes whether the recommended addon is required.
   *
   * @param required {@code true} if the recommend addon is required, otherwise {@code false}.
   */
  void setRequired(boolean required);

  /**
   * Retrieves the recommended addon as a {@link JsonObject}.
   *
   * @return The recommend addon as a json object.
   */
  JsonObject asJsonObject();

  /** A factory for creating {@link RecommendedAddon}'s. */
  interface Factory {

    /**
     * Creates a new {@link RecommendedAddon} with the {@code publishedUniqueId} and {@code
     * required} if it is needed.
     *
     * @param publishedUniqueId The published unique identifier of the recommended addon.
     * @param required {@code true} if the recommended addon should be required, otherwise {@code
     *     false}.
     * @return A created recommended addon.
     */
    RecommendedAddon create(@Assisted UUID publishedUniqueId, @Assisted boolean required);
  }
}
