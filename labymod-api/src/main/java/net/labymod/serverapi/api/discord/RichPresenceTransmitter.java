package net.labymod.serverapi.api.discord;

import com.google.gson.JsonObject;
import java.util.UUID;
import net.labymod.serverapi.api.player.LabyModPlayer;

public interface RichPresenceTransmitter {

  void updateRichPresenceTimer(LabyModPlayer<?> player, String gameMode, long startTime);

  void updateRichPresenceCountdown(LabyModPlayer<?> player, String gameMode, long endTime);

  default void updateRichPresence(
      LabyModPlayer<?> player, String gameMode, long startTime, long endTime) {
    this.updateRichPresence(player, true, gameMode, startTime, endTime);
  }

  void updateRichPresence(
      LabyModPlayer<?> player, boolean hasGame, String gameMode, long startTime, long endTime);

  default void updateParty(
      LabyModPlayer<?> player,
      UUID partyLeaderUniqueId,
      int partySize,
      int maximalPartyMembers,
      String domain) {
    this.updateParty(player, true, partyLeaderUniqueId, partySize, maximalPartyMembers, domain);
  }

  void updateParty(
      LabyModPlayer<?> player,
      boolean hasParty,
      UUID partyLeaderUniqueId,
      int partySize,
      int maximalPartyMembers,
      String domain);
}
