package net.labymod.serverapi.common.serverinteraction.actionmenu;

import com.google.gson.JsonObject;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import net.labymod.serverapi.api.serverinteraction.actionmenu.ActionType;
import net.labymod.serverapi.api.serverinteraction.actionmenu.MenuEntry;

public class DefaultMenuEntry implements MenuEntry {

  private final String displayName;
  private final String value;
  private final ActionType actionType;
  private final JsonObject entryObject;

  @AssistedInject
  private DefaultMenuEntry(
      @Assisted("displayName") String displayName,
      @Assisted("value") String value,
      @Assisted ActionType actionType) {
    this.displayName = displayName;
    this.value = value;
    this.actionType = actionType;
    this.entryObject = new JsonObject();

    this.entryObject.addProperty("displayName", displayName);
    this.entryObject.addProperty("value", value);
    this.entryObject.addProperty("type", actionType.name());
  }

  /** {@inheritDoc} */
  @Override
  public String getDisplayName() {
    return this.displayName;
  }

  /** {@inheritDoc} */
  @Override
  public String getValue() {
    return this.value;
  }

  /** {@inheritDoc} */
  @Override
  public ActionType getActionType() {
    return this.actionType;
  }

  /** {@inheritDoc} */
  @Override
  public JsonObject asJsonObject() {
    return this.entryObject;
  }
}
