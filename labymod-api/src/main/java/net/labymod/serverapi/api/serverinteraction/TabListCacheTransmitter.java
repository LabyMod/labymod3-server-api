package net.labymod.serverapi.api.serverinteraction;

import net.labymod.serverapi.api.player.LabyModPlayer;

public interface TabListCacheTransmitter {

  boolean isEnabled();

  void setEnabled(boolean enabled);

  void transmit(LabyModPlayer<?> player);
}
