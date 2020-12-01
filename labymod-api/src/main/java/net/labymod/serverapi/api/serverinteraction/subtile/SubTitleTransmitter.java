package net.labymod.serverapi.api.serverinteraction.subtile;

import java.util.UUID;

/**
 * Represents a subtitle transmitter that sends a payload message in channel <b>account_subtitle</b>
 * to the LabyMod client.
 *
 * <p><b>Overview:</b>
 *
 * <p>The LabyMod client can display a subtitle tag (similar through using the scoreboard) under a
 * players name.
 */
public interface SubTitleTransmitter {

  /**
   * Adds a subtitle to a list which will be displayed when sending.
   *
   * @param subTitle The subtitle which should be displayed above the player.
   * @return This object for a fluent chaining.
   */
  SubTitleTransmitter addSubtitle(SubTitle subTitle);

  /**
   * Adds to a list an array of subtitles which will be displayed when sending.
   *
   * @param subTitles An array subtitles which should be displayed above the player's.
   * @return This object for a fluent chaining.
   */
  SubTitleTransmitter addSubtitles(SubTitle... subTitles);

  /**
   * Transmits a list of all subtitles to the client.
   *
   * @param uniqueId The unique identifier of the receiver.
   */
  void transmit(UUID uniqueId);
}
