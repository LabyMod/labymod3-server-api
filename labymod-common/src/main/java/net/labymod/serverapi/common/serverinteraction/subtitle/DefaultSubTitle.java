package net.labymod.serverapi.common.serverinteraction.subtitle;

import com.google.gson.JsonObject;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import java.util.UUID;
import net.labymod.serverapi.api.serverinteraction.subtile.SubTitle;

public class DefaultSubTitle implements SubTitle {

  private UUID uniqueId;
  private String value;
  private JsonObject rawText;
  private double size;

  private JsonObject subTitleObject;

  @AssistedInject
  private DefaultSubTitle(@Assisted UUID uniqueId, @Assisted String value) {
    this(uniqueId, value, 0.8D);
  }

  @AssistedInject
  private DefaultSubTitle(@Assisted UUID uniqueId, @Assisted JsonObject rawText) {
    this(uniqueId, rawText, 0.8D);
  }

  @AssistedInject
  private DefaultSubTitle(@Assisted UUID uniqueId, @Assisted String value, @Assisted double size) {
    size = this.checkSize(size);

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

  @AssistedInject
  private DefaultSubTitle(
      @Assisted UUID uniqueId, @Assisted JsonObject rawText, @Assisted double size) {
    size = this.checkSize(size);

    this.uniqueId = uniqueId;
    this.rawText = rawText;
    this.size = size;

    this.subTitleObject = new JsonObject();

    this.subTitleObject.addProperty("uuid", uniqueId.toString());
    this.subTitleObject.addProperty("size", size);

    if (this.rawText != null) {
      this.subTitleObject.addProperty("raw_json_text", this.rawText.toString());
    }
  }

  private double checkSize(double size) {
    return size < 0.8D ? 0.8D : Math.min(size, 1.6D);
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
  public JsonObject getRawTextAsJson() {
    return this.rawText;
  }

  /** {@inheritDoc} */
  @Override
  public JsonObject asJsonObject() {
    return this.subTitleObject;
  }
}
