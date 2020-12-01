package net.labymod.serverapi.api.sticker;

import com.google.gson.JsonObject;
import java.util.UUID;

/** Represents a sticker. */
public interface Sticker {

  /**
   * Retrieves the unique identifier of a NPC where the sticker is to be played.
   *
   * @return The unique identifier of a NPC.
   */
  UUID getNPCUniqueId();

  /**
   * Retrieves the identifier of a sticker to be played.
   *
   * @return The identifier of a sticker.
   */
  short getStickerId();

  /**
   * Retrieves the sticker as a {@link JsonObject}.
   *
   * @return The sticker as a json object.
   */
  JsonObject asJsonObject();

  /** A factory for creating {@link Sticker}'s. */
  interface Factory {

    /**
     * Creates a new {@link Sticker} with the {@code npcUniqueId} and the {@code stickerId}.
     *
     * @param npcUniqueId The unique identifier of a NPC.
     * @param stickerId The identifier of a sticker.
     * @return A created sticker.
     */
    Sticker create(UUID npcUniqueId, short stickerId);
  }
}
