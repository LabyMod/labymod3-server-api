package net.labymod.serverapi.common.protocol;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import net.labymod.serverapi.api.protocol.ShadowProtocol;

public class DefaultShadowProtocol implements ShadowProtocol {

  private final int version;
  private final boolean enabled;

  @AssistedInject
  private DefaultShadowProtocol(@Assisted int version, @Assisted boolean enabled) {
    this.version = version;
    this.enabled = enabled;
  }

  /** {@inheritDoc} */
  @Override
  public int getVersion() {
    return this.version;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isEnabled() {
    return this.enabled;
  }
}
