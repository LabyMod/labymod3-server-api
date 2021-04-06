package net.labymod.serverapi.common.sticker;

import net.labymod.serverapi.api.sticker.Sticker;

import java.util.UUID;

public class DefaultStickerFactory implements Sticker.Factory {

  /** {@inheritDoc} */
  @Override
  public Sticker create(UUID npcUniqueId, short stickerId) {
    return new DefaultSticker(npcUniqueId, stickerId);
  }
}
