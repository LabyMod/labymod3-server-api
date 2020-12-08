package net.labymod.serverapi.common.protocol;

import net.labymod.serverapi.api.protocol.ShadowProtocol;

public class DefaultShadowProtocolFactory implements ShadowProtocol.Factory {

  private static final ShadowProtocol.Factory INSTANCE =
      new DefaultShadowProtocolFactory();

  private DefaultShadowProtocolFactory() {}

  public static ShadowProtocol.Factory getInstance() {
    return INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public ShadowProtocol create(int version, boolean enabled) {
    return new DefaultShadowProtocol(version, enabled);
  }
}
