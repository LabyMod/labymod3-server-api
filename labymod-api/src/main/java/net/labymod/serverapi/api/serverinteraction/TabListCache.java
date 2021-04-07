package net.labymod.serverapi.api.serverinteraction;

import java.util.UUID;

/** @deprecated Use {@link TabListCache} */
@Deprecated
public interface TabListCache {

  /** @deprecated Use {@link TabListCacheTransmitter#isEnabled()} */
  @Deprecated
  boolean isEnabled();

  /** @deprecated Use {@link TabListCacheTransmitter#setEnabled(boolean)} */
  @Deprecated
  void setEnabled(boolean enabled);

  /** @deprecated Use {@link TabListCacheTransmitter#transmit(UUID)} */
  @Deprecated
  void transmit(UUID uniqueId);

  /** @deprecated Use {@link TabListCacheTransmitter#transmitBroadcast()} */
  @Deprecated
  void transmitBroadcast();
}
