package net.labymod.serverapi.api.serverinteraction;

import java.util.UUID;

public interface TabListCache {

  boolean isEnabled();

  void setEnabled(boolean enabled);

  void transmit(UUID uniqueId);

  void transmitBroadcast();
}
