package net.labymod.serverapi.common.serverinteraction.economy;

import com.google.gson.JsonObject;
import java.util.UUID;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.serverinteraction.economy.EconomyBalanceType;
import net.labymod.serverapi.api.serverinteraction.economy.EconomyDisplay;

public class DefaultEconomyDisplay implements EconomyDisplay {

  private static final String ECONOMY_CHANNEL = "economy";

  private final PayloadCommunicator payloadCommunicator;

  public DefaultEconomyDisplay(PayloadCommunicator payloadCommunicator) {
    this.payloadCommunicator = payloadCommunicator;
  }

  /** {@inheritDoc} */
  @Override
  public void sendBalanceDisplay(
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
}
