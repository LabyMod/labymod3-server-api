package net.labymod.serverapi.common.emote;

import com.google.gson.JsonArray;
import net.labymod.serverapi.api.LabyService;
import net.labymod.serverapi.api.emote.Emote;
import net.labymod.serverapi.api.emote.EmoteTransmitter;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.player.LabyModPlayerService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/** Default implementation of the {@link EmoteTransmitter}. */
public class DefaultEmoteTransmitter implements EmoteTransmitter {

  private static final String EMOTE_API_CHANNEL = "emote_api";

  private final LabyModPlayerService<?> playerService;
  private final PayloadCommunicator payloadCommunicator;
  private final List<Emote> emotes;

  public DefaultEmoteTransmitter(LabyService service) {
    this.playerService = service.getLabyPlayerService();
    this.payloadCommunicator = service.getPayloadCommunicator();
    this.emotes = new ArrayList<>();
  }

  /** {@inheritDoc} */
  @Override
  public EmoteTransmitter addEmote(Emote emote) {
    this.emotes.add(emote);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public EmoteTransmitter addEmotes(Emote... emotes) {
    this.emotes.addAll(Arrays.asList(emotes));
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public void transmit(UUID receiverUniqueId) {
    JsonArray emotes = new JsonArray();

    for (Emote emote : this.emotes) {
      emotes.add(emote.asJsonObject());
    }
    this.emotes.clear();
    this.payloadCommunicator.sendLabyModMessage(receiverUniqueId, EMOTE_API_CHANNEL, emotes);
  }

  @Override
  public void broadcastTransmit() {
    for (LabyModPlayer<?> player : this.playerService.getPlayers()) {
      this.transmit(player.getUniqueId());
    }
  }
}
