package net.labymod.serverapi.common.serverinteraction.subtitle;

import com.google.gson.JsonObject;
import java.util.UUID;
import net.labymod.serverapi.api.serverinteraction.subtile.SubTitle;

public class DefaultSubTitleFactory implements SubTitle.Factory {

  /** {@inheritDoc} */
  @Override
  public SubTitle create(UUID uniqueId, String value) {
    return new DefaultSubTitle(uniqueId, value);
  }

  /** {@inheritDoc} */
  @Override
  public SubTitle create(UUID uniqueId, JsonObject rawText) {
    return new DefaultSubTitle(uniqueId, rawText);
  }

  /** {@inheritDoc} */
  @Override
  public SubTitle create(UUID uniqueId, String value, double size) {
    return new DefaultSubTitle(uniqueId, value, size);
  }

  /** {@inheritDoc} */
  @Override
  public SubTitle create(UUID uniqueId, JsonObject rawText, double size) {
    return new DefaultSubTitle(uniqueId, rawText, size);
  }
}
