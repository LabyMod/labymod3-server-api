package net.labymod.serverapi.api.serverinteraction.subtile;

import com.google.gson.JsonObject;
import java.util.UUID;

/** Represents a sub title. */
public interface SubTitle {

  /**
   * Retrieves the unique identifier of the player for which the subtitle should be displayed.
   *
   * @return The unique identifier of the player for which the subtitle should be displayed.
   */
  UUID getUniqueId();

  /**
   * Retrieves the value of the subtitle.
   *
   * @return The value of the subtitle.
   */
  String getValue();

  /**
   * Retrieves the size of the subtitle.
   *
   * @return The size of the subtitle.
   */
  double getSize();

  /**
   * Retrieves the subtitle as a {@link JsonObject}.
   *
   * @return The subtitle as a json object.
   */
  JsonObject asJsonObject();

  /** A factory for creating {@link SubTitle}'s. */
  interface Factory {

    /**
     * Creates a new {@link SubTitle} with the given {@code uniqueId} and {@code value}.
     *
     * <p><b>Note:</b>
     *
     * <p>This creates a subtitle which is {@code 0.8D} in size.
     *
     * @param uniqueId The unique identifier of the player for which the subtitle should be
     *     displayed.
     * @param value The value of the subtitle.
     * @return A created subtitle.
     */
    SubTitle create(UUID uniqueId, String value);

    /**
     * Creates a new {@link SubTitle} with the given {@code uniqueId}, {@code value} and the {@code
     * size}.
     *
     * <p><b>Note:</b>
     *
     * <p>The smallest subtitle size is {@code 0.8D} and the largest is {@code 1.6D} which is the
     * size of the normal Minecraft tag.
     *
     * @param uniqueId The unique identifier of the player for which the subtitle should be
     *     displayed.
     * @param value The value of the subtitle.
     * @param size The size of the subtitle.
     * @return A created subtitle.
     */
    SubTitle create(UUID uniqueId, String value, double size);
  }
}
