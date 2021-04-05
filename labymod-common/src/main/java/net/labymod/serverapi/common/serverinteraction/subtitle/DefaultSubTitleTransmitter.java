package net.labymod.serverapi.common.serverinteraction.subtitle;

import com.google.gson.JsonArray;
import java.util.UUID;
import net.labymod.serverapi.api.LabyService;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.api.serverinteraction.subtile.SubTitle;
import net.labymod.serverapi.api.serverinteraction.subtile.SubTitleTransmitter;

public class DefaultSubTitleTransmitter implements SubTitleTransmitter {

  private static final String ACCOUNT_SUBTITLE_CHANNEL = "account_subtitle";

  private final LabyModPlayerService<?> playerService;
  private final PayloadCommunicator payloadCommunicator;
  private final JsonArray subTitles;

  public DefaultSubTitleTransmitter(LabyService service) {
    this.payloadCommunicator = service.getPayloadCommunicator();
    this.playerService = service.getLabyPlayerService();
    this.subTitles = new JsonArray();
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
  }

  /** {@inheritDoc} */
  @Override
  public void broadcastTransmit() {
    for (LabyModPlayer<?> player : this.playerService.getPlayers()) {
      this.transmit(player.getUniqueId());
    }
  }
}
