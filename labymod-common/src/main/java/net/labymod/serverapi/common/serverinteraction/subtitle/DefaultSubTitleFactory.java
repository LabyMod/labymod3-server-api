package net.labymod.serverapi.common.serverinteraction.subtitle;

import java.util.UUID;
import net.labymod.serverapi.api.serverinteraction.subtile.SubTitle;

public class DefaultSubTitleFactory implements SubTitle.Factory {

  /** {@inheritDoc} */
  @Override
  public SubTitle create(UUID uniqueId, String value) {
    return new DefaultSubTitle(uniqueId, value, 0.8D);
  }

  /** {@inheritDoc} */
  @Override
  public SubTitle create(UUID uniqueId, String value, double size) {
    return new DefaultSubTitle(uniqueId, value, size);
  }
}
