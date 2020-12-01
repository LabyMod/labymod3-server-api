package net.labymod.serverapi.api.sticker;

import java.util.UUID;
import net.labymod.serverapi.api.emote.EmoteTransmitter;

/**
 * Represents a sticker transmitter that sends a payload message in channel <b>sticker_api</b> to
 * the LabyMod client.
 *
 * <p><b>Overview:</b>
 *
 * <p>LabyMod users can play client side a sticker for all other players on the minecraft server.
 * Minecraft servers can also force-play stickers for specifc player's using the plugin messages.
 *
 * <p><b>Warning:</b>
 *
 * <p>This works only for NPC's and not for real players.
 *
 * <p>
 *
 * <p>The behavior is the same as in the emote api ({@link EmoteTransmitter}).
 *
 * <p><a href="https://dl.labymod.net/stickers.json">A list of all available stickers.</a>
 */
public interface StickerTransmitter {

  /**
   * Adds a sticker to a list to be played when sending.
   *
   * @param sticker The sticker which should be played.
   * @return This object for a fluent chaining.
   */
  StickerTransmitter addSticker(Sticker sticker);

  /**
   * ADds to a list an array of stickers to be played when sending.
   *
   * @param stickers An array stickers which should be played.
   * @return This object for a fluent chaining.
   */
  StickerTransmitter addStickers(Sticker... stickers);

  /**
   * Transmits a list of all forced stickers to the client.
   *
   * @param receiverUniqueId The unique identifier of the receiver.
   */
  void transmitSticker(UUID receiverUniqueId);
}
