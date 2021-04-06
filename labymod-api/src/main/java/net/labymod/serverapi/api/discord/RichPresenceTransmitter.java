package net.labymod.serverapi.api.discord;

import java.util.UUID;

public interface RichPresenceTransmitter {

  void updateRichPresenceTimer(UUID receiverUniqueId, String gameMode, long startTime);

  void updateRichPresenceCountdown(UUID receiverUniqueId, String gameMode, long endTime);

  default void updateRichPresence(
      UUID receiverUniqueId, String gameMode, long startTime, long endTime) {
    this.updateRichPresence(receiverUniqueId, true, gameMode, startTime, endTime);
  }

  void updateRichPresence(
      UUID uniqueId, boolean hasGame, String gameMode, long startTime, long endTime);

  default void updateParty(
      UUID receiverUniqueId,
      UUID partyLeaderUniqueId,
      int partySize,
      int maximalPartyMembers,
      String domain) {
    this.updateParty(receiverUniqueId, true, partyLeaderUniqueId, partySize, maximalPartyMembers, domain);
  }

  void updateParty(
      UUID receiverUniqueId,
      boolean hasParty,
      UUID partyLeaderUniqueId,
      int partySize,
      int maximalPartyMembers,
      String domain);
}
