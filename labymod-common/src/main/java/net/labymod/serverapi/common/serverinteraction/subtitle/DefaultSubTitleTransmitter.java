package net.labymod.serverapi.common.serverinteraction.subtitle;

import com.google.gson.JsonArray;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.UUID;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.serverinteraction.subtile.SubTitle;
import net.labymod.serverapi.api.serverinteraction.subtile.SubTitleTransmitter;

@Singleton
public class DefaultSubTitleTransmitter implements SubTitleTransmitter {

  private static final String ACCOUNT_SUBTITLE_CHANNEL = "account_subtitle";

  private final PayloadCommunicator payloadCommunicator;
  private final JsonArray subTitles;

  @Inject
  private DefaultSubTitleTransmitter(PayloadCommunicator payloadCommunicator) {
    this.payloadCommunicator = payloadCommunicator;
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
}
