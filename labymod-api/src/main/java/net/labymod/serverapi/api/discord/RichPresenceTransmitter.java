package net.labymod.serverapi.api.discord;

import java.util.UUID;

/**
 * Represents the transmitter for the rich presence feature.
 *
 * <p><b>Overview:</b>
 *
 * <p>The discord utility of LabyMod allows users to utilize the <a
 * href="https://discordapp.com/rich-presence">Discord Rich Presence</a> feature. The user can then
 * share his current server and game (for example the specific lobby he is on) using Discord.
 */
public interface RichPresenceTransmitter {

  /**
   * Updates the rich presence as a timer.
   *
   * @param receiverUniqueId The unique identifier of the receiver.
   * @param gameMode The name of the game mode.
   * @param startTime The start time when the game started.
   */
  void updateRichPresenceTimer(UUID receiverUniqueId, String gameMode, long startTime);

  /**
   * Updates the rich presence as a countdown.
   *
   * @param receiverUniqueId The unique identifier of the receiver.
   * @param gameMode The name of the game mode.
   * @param endTime The end time when the game ended.
   */
  void updateRichPresenceCountdown(UUID receiverUniqueId, String gameMode, long endTime);

  /**
   * Updates the rich presence.
   *
   * @param receiverUniqueId The unique identifier of the receiver.
   * @param gameMode The name of the game mode.
   * @param startTime The start time when the game started. <b>Note:</b> Set to 0 for countdown.
   * @param endTime The end time when the game ended. <b>Note:</b> Set to 0 for timer.
   */
  default void updateRichPresence(
      UUID receiverUniqueId, String gameMode, long startTime, long endTime) {
    this.updateRichPresence(receiverUniqueId, true, gameMode, startTime, endTime);
  }

  /**
   * Updates the rich presence.
   *
   * @param uniqueId The unique identifier of the receiver.
   * @param hasGame {@code true} if the player has a game, otherwise {@code false}.
   * @param gameMode The name of the game mode.
   * @param startTime The start time when the game started. <b>Note:</b> Set to 0 for countdown.
   * @param endTime The end time when the game ended. <b>Note:</b> Set to 0 for timer.
   */
  void updateRichPresence(
      UUID uniqueId, boolean hasGame, String gameMode, long startTime, long endTime);

  /**
   * Updates the party information.
   *
   * @param receiverUniqueId The unique identifier of the receiver.
   * @param partyLeaderUniqueId The unique identifier of the party leader.
   * @param partySize The size of the party.
   * @param maximalPartyMembers The maximal members of the party.
   * @param domain The domain from the network.
   */
  default void updateParty(
      UUID receiverUniqueId,
      UUID partyLeaderUniqueId,
      int partySize,
      int maximalPartyMembers,
      String domain) {
    this.updateParty(
        receiverUniqueId, true, partyLeaderUniqueId, partySize, maximalPartyMembers, domain);
  }

  /**
   * Updates the party information.
   *
   * @param receiverUniqueId The unique identifier of the receiver.
   * @param hasParty {@code true} if the player has a party, otherwise {@code false}.
   * @param partyLeaderUniqueId The unique identifier of the party leader.
   * @param partySize The size of the party.
   * @param maximalPartyMembers The maximal members of the party.
   * @param domain The domain from the network.
   */
  void updateParty(
      UUID receiverUniqueId,
      boolean hasParty,
      UUID partyLeaderUniqueId,
      int partySize,
      int maximalPartyMembers,
      String domain);
}
