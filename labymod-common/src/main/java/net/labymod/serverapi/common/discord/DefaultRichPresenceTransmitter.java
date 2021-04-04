package net.labymod.serverapi.common.discord;

import com.google.gson.JsonObject;
import java.util.UUID;
import net.labymod.serverapi.api.LabyService;
import net.labymod.serverapi.api.discord.RichPresenceTransmitter;
import net.labymod.serverapi.api.payload.PayloadCommunicator;

/** Default implementation of {@link RichPresenceTransmitter}. */
public class DefaultRichPresenceTransmitter implements RichPresenceTransmitter {

  private static final String DISCORD_RPC_CHANNEL = "discord_rpc";
  private final PayloadCommunicator payloadCommunicator;

  public DefaultRichPresenceTransmitter(LabyService service) {
    this.payloadCommunicator = service.getPayloadCommunicator();
  }

  /** {@@inheritDoc} */
  @Override
  public void updateRichPresenceTimer(UUID receiverUniqueId, String gameMode, long startTime) {
    this.updateRichPresence(receiverUniqueId, gameMode, startTime, 0L);
  }

  /** {@@inheritDoc} */
  @Override
  public void updateRichPresenceCountdown(UUID receiverUniqueId, String gameMode, long endTime) {
    this.updateRichPresence(receiverUniqueId, gameMode, 0L, System.currentTimeMillis());
  }

  /** {@@inheritDoc} */
  @Override
  public void updateRichPresence(
      UUID receiverUniqueId, boolean hasGame, String gameMode, long startTime, long endTime) {

    JsonObject richPresenceObject = new JsonObject();

    richPresenceObject.addProperty("hasGame", hasGame);

    if (hasGame) {
      richPresenceObject.addProperty("game_mode", gameMode);
      richPresenceObject.addProperty("game_startTime", startTime);
      richPresenceObject.addProperty("game_endTime", endTime);
    }

    this.payloadCommunicator.sendLabyModMessage(
        receiverUniqueId, DISCORD_RPC_CHANNEL, richPresenceObject);
  }

  /** {@inheritDoc} */
  @Override
  public void updateParty(
      UUID receiverUniqueId,
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

    this.payloadCommunicator.sendLabyModMessage(receiverUniqueId, DISCORD_RPC_CHANNEL, partyObject);
  }
}
