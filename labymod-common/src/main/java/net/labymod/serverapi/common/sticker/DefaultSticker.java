package net.labymod.serverapi.common.sticker;

import com.google.gson.JsonObject;
import java.util.UUID;
import net.labymod.serverapi.api.sticker.Sticker;

public class DefaultSticker implements Sticker {

  private final UUID npcUniqueId;
  private final short stickerId;
  private final JsonObject stickerObject;

  public DefaultSticker(UUID npcUniqueId, short stickerId) {
    this.npcUniqueId = npcUniqueId;
    this.stickerId = stickerId;
    this.stickerObject = new JsonObject();
    this.stickerObject.addProperty("uuid", npcUniqueId.toString());
    this.stickerObject.addProperty("sticker_id", stickerId);
  }

  /** {@inheritDoc} */
  @Override
  public UUID getNPCUniqueId() {
    return this.npcUniqueId;
  }

  /** {@inheritDoc} */
  @Override
  public short getStickerId() {
    return this.stickerId;
  }

  /** {@inheritDoc} */
  @Override
  public JsonObject asJsonObject() {
    return this.stickerObject;
  }
}
