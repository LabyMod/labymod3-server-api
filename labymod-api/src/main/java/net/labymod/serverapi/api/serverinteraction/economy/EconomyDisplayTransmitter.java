package net.labymod.serverapi.api.serverinteraction.economy;

import java.util.UUID;

/**
 * Represents the economy display packet.
 *
 * <p><b>Overview:</b>
 *
 * <p>It is also possible to display the balance of your economy system. If the user enters a shop
 * or is in a trading situation, you can display the amount of the balance in the top right corner.
 * You can also use this feature in games if you have to collect coins.
 */
public interface EconomyDisplayTransmitter {

  /**
   * Transmits the balance display to the LabyMod client.
   *
   * @param uniqueId The unique identifier of the receiver.
   * @param economyBalanceType The type of the economy balance.
   * @param showBalanceDisplay {@code true} shows the balance display, otherwise {@code false}.
   * @param balance The balance of the receiver.
   */
  void transmit(
      UUID uniqueId,
      EconomyBalanceType economyBalanceType,
      boolean showBalanceDisplay,
      int balance);

  /**
   * Transmits the balance display to all online laby users.
   *
   * @param economyBalanceType The type of the economy balance.
   * @param showBalanceDisplay {@code true} shows the balance display, otherwise {@code false}.
   * @param balance The balance of the receiver.
   */
  void broadcastTransmit(
      EconomyBalanceType economyBalanceType, boolean showBalanceDisplay, int balance);
}
