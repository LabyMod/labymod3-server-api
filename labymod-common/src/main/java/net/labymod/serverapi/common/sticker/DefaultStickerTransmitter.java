package net.labymod.serverapi.common.sticker;

import com.google.gson.JsonArray;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import net.labymod.serverapi.api.LabyService;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.api.sticker.Sticker;
import net.labymod.serverapi.api.sticker.StickerTransmitter;

public class DefaultStickerTransmitter implements StickerTransmitter {

  private static final String STICKER_API_CHANNEL = "sticker_api";

  private final PayloadCommunicator payloadCommunicator;
  private final LabyModPlayerService<?> labyPlayerService;
  private final List<Sticker> stickers;

  public DefaultStickerTransmitter(LabyService service) {
    this.payloadCommunicator = service.getPayloadCommunicator();
    this.labyPlayerService = service.getLabyPlayerService();
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
  public void transmit(UUID receiverUniqueId) {
    JsonArray stickers = new JsonArray();

    for (Sticker sticker : this.stickers) {
      stickers.add(sticker.asJsonObject());
    }
    this.stickers.clear();

    this.payloadCommunicator.sendLabyModMessage(receiverUniqueId, STICKER_API_CHANNEL, stickers);
  }

  /** {@inheritDoc} */
  @Override
  public void broadcastTransmit() {
    for (LabyModPlayer<?> player : this.labyPlayerService.getPlayers()) {
      this.transmit(player.getUniqueId());
    }
  }
}
