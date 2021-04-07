package net.labymod.serverapi.common.serverinteraction.economy;

import com.google.gson.JsonObject;
import java.util.UUID;
import net.labymod.serverapi.api.LabyService;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.api.serverinteraction.economy.EconomyBalanceType;
import net.labymod.serverapi.api.serverinteraction.economy.EconomyDisplayTransmitter;

public class DefaultEconomyDisplayTransmitter implements EconomyDisplayTransmitter {

  private static final String ECONOMY_CHANNEL = "economy";

  private final PayloadCommunicator payloadCommunicator;
  private final LabyModPlayerService<?> playerService;

  public DefaultEconomyDisplayTransmitter(LabyService service) {
    this.payloadCommunicator = service.getPayloadCommunicator();
    this.playerService = service.getLabyPlayerService();
  }

  /** {@inheritDoc} */
  @Override
  public void transmit(
      UUID uniqueId,
      EconomyBalanceType economyBalanceType,
      boolean showBalanceDisplay,
      int balance) {
    JsonObject economyObject = new JsonObject();
    JsonObject cashObject = new JsonObject();

    cashObject.addProperty("visible", showBalanceDisplay);
    cashObject.addProperty("balance", balance);

    economyObject.add(economyBalanceType.getKey(), cashObject);

    this.payloadCommunicator.sendLabyModMessage(uniqueId, ECONOMY_CHANNEL, economyObject);
  }

  /** {@inheritDoc} */
  @Override
  public void broadcastTransmit(
      EconomyBalanceType economyBalanceType, boolean showBalanceDisplay, int balance) {
    for (LabyModPlayer<?> player : this.playerService.getPlayers()) {
      this.transmit(player.getUniqueId(), economyBalanceType, showBalanceDisplay, balance);
    }
  }
}
