package net.labymod.serverapi.api.serverinteraction.economy;

/** An enumeration that representing all available economy balance types. */
public enum EconomyBalanceType {
  CASH,
  BANK;

  /**
   * Retrieves the name of the economy balance type as lowercase name.
   *
   * @return The name of the economy balance type as lowercase name.
   */
  public String getKey() {
    return this.name().toLowerCase();
  }
}
