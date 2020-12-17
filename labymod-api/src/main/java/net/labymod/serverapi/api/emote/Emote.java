package net.labymod.serverapi.api.emote;

import com.google.gson.JsonObject;
import com.google.inject.assistedinject.Assisted;
import java.util.UUID;

/** Represents an emote. */
public interface Emote {

  /**
   * Retrieves the unique identifier of a NPC where the emote is to be played.
   *
   * @return The unique identifier of a NPC.
   */
  UUID getNPCUniqueId();

  /**
   * Retrieves the identifier of an emote to be played.
   *
   * @return The identifier of an emote.
   */
  int getEmoteId();

  /**
   * Retrieves the emote as a {@link JsonObject}.
   *
   * @return The emote as a json object.
   */
  JsonObject asJsonObject();

  /** A factory for creating {@link Emote}. */
  interface Factory {

    /**
     * Creates a new {@link Emote} with the {@code npcUniqueId} and the {@code emoteId}.
     *
     * @param npcUniqueId The unique identifier of a NPC.
     * @param emoteId The identifier of an emote.
     * @return A created emote.
     */
    Emote create(@Assisted UUID npcUniqueId, @Assisted int emoteId);
  }
}
