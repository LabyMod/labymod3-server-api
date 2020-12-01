package net.labymod.serverapi.common.protocol;

import net.labymod.serverapi.api.protocol.ShadowProtocol;

public class DefaultShadowProtocolFactory implements ShadowProtocol.Factory {

  /** {@inheritDoc} */
  @Override
  public ShadowProtocol create(int version, boolean enabled) {
    return new DefaultShadowProtocol(version, enabled);
  }
}
