package net.labymod.serverapi.api.emote;

import java.util.UUID;
import net.labymod.serverapi.api.serverinteraction.Watermark;

/**
 * Represents an emote transmitter that sends a payload message in channel <b>emote_api</b> to the
 * LabyMod client.
 *
 * <p><b>Overview:</b>
 *
 * <p>LabyMod users can play client side an emote for all other players on the Minecraft server.
 * Minecraft servers can also force-play emotes for specific player's using the payload messages.
 *
 * <p><b>Warning:</b> This works only for NPC's and not for real players.
 *
 * <p>Servers can make NPC's do emotes. To prevent abuse, this does not work for real players. To
 * enforce this, emotes can only be forced for players that have the second half of their {@link
 * UUID} entirely being 0 (64 least significant bits are 0, or the second long value equals 0) You
 * therefore need to spawn them with a unique identifier like this.
 *
 * <p>This rule does not apply if the {@link Watermark} feature is activated.
 */
public interface EmoteTransmitter {

  /**
   * Adds an emote to a list to be played when sending.
   *
   * @param emote The emote which should be played.
   * @return This object for a fluent chaining.
   */
  EmoteTransmitter addEmote(Emote emote);

  /**
   * Adds to a list an array of emotes to be played when sending.
   *
   * @param emotes An array emotes which should be played.
   * @return This object for a fluent chaining.
   */
  EmoteTransmitter addEmotes(Emote... emotes);

  /**
   * Transmits a list of all forced emotes to the client.
   *
   * @param receiverUniqueId The unique identifier of the receiver.
   */
  void transmitEmote(UUID receiverUniqueId);
}
