package net.labymod.serverapi.common.emote;

import com.google.gson.JsonArray;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import net.labymod.serverapi.api.emote.Emote;
import net.labymod.serverapi.api.emote.EmoteTransmitter;
import net.labymod.serverapi.api.payload.PayloadCommunicator;

/** Default implementation of the {@link EmoteTransmitter}. */
public class DefaultEmoteTransmitter implements EmoteTransmitter {

  private static final String EMOTE_API_CHANNEL = "emote_api";

  private final PayloadCommunicator payloadCommunicator;
  private final List<Emote> emotes;

  public DefaultEmoteTransmitter(PayloadCommunicator payloadCommunicator) {
    this.payloadCommunicator = payloadCommunicator;
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
  public void transmitEmote(UUID receiverUniqueId) {
    JsonArray emotes = new JsonArray();

    for (Emote emote : this.emotes) {
      emotes.add(emote.asJsonObject());
    }
    this.emotes.clear();
    this.payloadCommunicator.sendLabyModMessage(receiverUniqueId, EMOTE_API_CHANNEL, emotes);
  }
}
