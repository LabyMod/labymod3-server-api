package net.labymod.serverapi.common.sticker;

import com.google.gson.JsonArray;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.sticker.Sticker;
import net.labymod.serverapi.api.sticker.StickerTransmitter;

@Singleton
public class DefaultStickerTransmitter implements StickerTransmitter {

  private static final String STICKER_API_CHANNEL = "sticker_api";

  private final PayloadCommunicator payloadCommunicator;
  private final List<Sticker> stickers;

  @Inject
  private DefaultStickerTransmitter(PayloadCommunicator payloadCommunicator) {
    this.payloadCommunicator = payloadCommunicator;
    this.stickers = new ArrayList<>();
  }

  /** {@inheritDoc} */
  @Override
  public StickerTransmitter addSticker(Sticker sticker) {
    this.stickers.add(sticker);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public StickerTransmitter addStickers(Sticker... stickers) {
    this.stickers.addAll(Arrays.asList(stickers));
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public void transmitSticker(UUID receiverUniqueId) {
    JsonArray stickers = new JsonArray();

    for (Sticker sticker : this.stickers) {
      stickers.add(sticker.asJsonObject());
    }
    this.stickers.clear();

    this.payloadCommunicator.sendLabyModMessage(receiverUniqueId, STICKER_API_CHANNEL, stickers);
  }
}
