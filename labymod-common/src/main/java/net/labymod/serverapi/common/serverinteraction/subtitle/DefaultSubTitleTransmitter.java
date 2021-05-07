package net.labymod.serverapi.common.serverinteraction.subtitle;

import com.google.gson.JsonArray;
import net.labymod.serverapi.api.LabyService;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.api.serverinteraction.subtile.SubTitle;
import net.labymod.serverapi.api.serverinteraction.subtile.SubTitleTransmitter;

import java.util.UUID;

public class DefaultSubTitleTransmitter implements SubTitleTransmitter {

  private static final String ACCOUNT_SUBTITLE_CHANNEL = "account_subtitle";

  private final LabyModPlayerService<?> playerService;
  private final PayloadCommunicator payloadCommunicator;
  private JsonArray subTitles;

  private boolean broadcasting;

  public DefaultSubTitleTransmitter(LabyService service) {
    this.payloadCommunicator = service.getPayloadCommunicator();
    this.playerService = service.getLabyPlayerService();
    this.subTitles = new JsonArray();
    this.broadcasting = false;
  }

  /** {@inheritDoc} */
  @Override
  public SubTitleTransmitter addSubtitle(SubTitle subTitle) {
    this.subTitles.add(subTitle.asJsonObject());
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public SubTitleTransmitter addSubtitles(SubTitle... subTitles) {
    for (SubTitle subTitle : subTitles) {
      this.subTitles.add(subTitle.asJsonObject());
    }
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public void transmit(UUID uniqueId) {
    this.payloadCommunicator.sendLabyModMessage(uniqueId, ACCOUNT_SUBTITLE_CHANNEL, this.subTitles);
    if(!this.broadcasting) {
      // Resets the sub titles array
      this.subTitles = new JsonArray();
    }
  }

  /** {@inheritDoc} */
  @Override
  public void broadcastTransmit() {
    this.broadcasting = true;
    for (LabyModPlayer<?> player : this.playerService.getPlayers()) {
      this.transmit(player.getUniqueId());
    }
    // Resets the sub titles array
    this.subTitles = new JsonArray();
    this.broadcasting = false;
  }
}
