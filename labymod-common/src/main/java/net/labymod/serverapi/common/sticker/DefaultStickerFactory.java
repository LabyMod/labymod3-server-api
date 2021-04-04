package net.labymod.serverapi.common.sticker;

import java.util.UUID;
import net.labymod.serverapi.api.sticker.Sticker;

public class DefaultStickerFactory implements Sticker.Factory {

  /** {@inheritDoc} */
  @Override
  public Sticker create(UUID npcUniqueId, short stickerId) {
    return new DefaultSticker(npcUniqueId, stickerId);
  }
}
