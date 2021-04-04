package net.labymod.serverapi.common.discord;

import com.google.gson.JsonObject;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.UUID;
import net.labymod.serverapi.api.discord.RichPresenceTransmitter;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.player.LabyModPlayer;

/** Default implementation of {@link RichPresenceTransmitter}. */
@Singleton
public class DefaultRichPresenceTransmitter implements RichPresenceTransmitter {

  private static final String DISCORD_RPC_CHANNEL = "discord_rpc";
  private final PayloadCommunicator payloadCommunicator;

  @Inject
  private DefaultRichPresenceTransmitter(PayloadCommunicator payloadCommunicator) {
    this.payloadCommunicator = payloadCommunicator;
  }

  /** {@@inheritDoc} */
  @Override
  public void updateRichPresenceTimer(LabyModPlayer<?> player, String gameMode, long startTime) {
    this.updateRichPresence(player, gameMode, startTime, 0L);
  }

  /** {@@inheritDoc} */
  @Override
  public void updateRichPresenceCountdown(LabyModPlayer<?> player, String gameMode, long endTime) {
    this.updateRichPresence(player, gameMode, 0L, System.currentTimeMillis());
  }

  /** {@@inheritDoc} */
  @Override
  public void updateRichPresence(
      LabyModPlayer<?> player, boolean hasGame, String gameMode, long startTime, long endTime) {

    JsonObject richPresenceObject = new JsonObject();

    richPresenceObject.addProperty("hasGame", hasGame);

    if (hasGame) {
      richPresenceObject.addProperty("game_mode", gameMode);
      richPresenceObject.addProperty("game_startTime", startTime);
      richPresenceObject.addProperty("game_endTime", endTime);
    }

    this.payloadCommunicator.sendLabyModMessage(
        player.getUniqueId(), "discord_rpc", richPresenceObject);
  }

  /** {@inheritDoc} */
  @Override
  public void updateParty(
      LabyModPlayer<?> player,
      boolean hasParty,
      UUID partyLeaderUniqueId,
      int partySize,
      int maximalPartyMembers,
      String domain) {

    JsonObject partyObject = new JsonObject();

    partyObject.addProperty("hasParty", hasParty);

    if (hasParty) {
      partyObject.addProperty("partyId", partyLeaderUniqueId.toString() + ":" + domain);
      partyObject.addProperty("party_size", partySize);
      partyObject.addProperty("party_max", maximalPartyMembers);
    }

    this.payloadCommunicator.sendLabyModMessage(
        player.getUniqueId(), DISCORD_RPC_CHANNEL, partyObject);
  }
}
