package net.labymod.serverapi.common.serverinteraction.subtitle;

import com.google.gson.JsonObject;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import java.util.UUID;
import net.labymod.serverapi.api.serverinteraction.subtile.SubTitle;

public class DefaultSubTitle implements SubTitle {

  private final UUID uniqueId;
  private final String value;
  private final double size;

  private final JsonObject subTitleObject;

  @AssistedInject
  private DefaultSubTitle(@Assisted UUID uniqueId, @Assisted String value) {
    this(uniqueId, value, 0.8D);
  }

  @AssistedInject
  private DefaultSubTitle(@Assisted UUID uniqueId, @Assisted String value, @Assisted double size) {
    if (size < 0.8D) {
      size = 0.8D;
    } else if (size > 1.6D) {
      size = 1.6D;
    }

    this.uniqueId = uniqueId;
    this.value = value;
    this.size = size;
    this.subTitleObject = new JsonObject();

    this.subTitleObject.addProperty("uuid", uniqueId.toString());

    this.subTitleObject.addProperty("size", size);

    if (this.value != null) {
      this.subTitleObject.addProperty("value", value);
    }
  }

  /** {@inheritDoc} */
  @Override
  public UUID getUniqueId() {
    return this.uniqueId;
  }

  /** {@inheritDoc} */
  @Override
  public String getValue() {
    return this.value;
  }

  /** {@inheritDoc} */
  @Override
  public double getSize() {
    return this.size;
  }

  /** {@inheritDoc} */
  @Override
  public JsonObject asJsonObject() {
    return this.subTitleObject;
  }
}
