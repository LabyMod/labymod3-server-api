package net.labymod.serverapi.common.emote;

import com.google.gson.JsonObject;
import java.util.UUID;
import net.labymod.serverapi.api.emote.Emote;

public class DefaultEmote implements Emote {

  private final UUID npcUniqueId;
  private final int emoteId;
  private final JsonObject emoteObject;

  public DefaultEmote(UUID npcUniqueId, int emoteId) {
    this.npcUniqueId = npcUniqueId;
    this.emoteId = emoteId;
    this.emoteObject = new JsonObject();

    this.emoteObject.addProperty("uuid", npcUniqueId.toString());
    this.emoteObject.addProperty("emote_id", emoteId);
  }

  /** {@inheritDoc} */
  @Override
  public UUID getNPCUniqueId() {
    return this.npcUniqueId;
  }

  /** {@inheritDoc} */
  @Override
  public int getEmoteId() {
    return this.emoteId;
  }

  /** {@inheritDoc} */
  @Override
  public JsonObject asJsonObject() {
    return this.emoteObject;
  }
}
