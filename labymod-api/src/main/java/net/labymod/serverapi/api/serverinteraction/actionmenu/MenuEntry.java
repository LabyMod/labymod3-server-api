package net.labymod.serverapi.api.serverinteraction.actionmenu;

import com.google.gson.JsonObject;

/** Represents a menu entry. */
public interface MenuEntry {

  /**
   * Retrieves the display name of the menu entry.
   *
   * @return The display name of the entry.
   */
  String getDisplayName();

  /**
   * Retrieves the value of the menu entry.
   *
   * <p><b>Note:</b>
   *
   * <p>`{name}` will be replaced with the players name.
   *
   * @return The value of the menu entry.
   */
  String getValue();

  /**
   * Retrieves the action type of the menu entry.
   *
   * @return The action type of the entry.
   */
  ActionType getActionType();

  /**
   * Retrieves the menu entry as a {@link JsonObject}.
   *
   * @return The menu entry as a json object.
   */
  JsonObject asJsonObject();

  /** A factory for creating {@link MenuEntry}'s. */
  interface Factory {

    /**
     * Creates a new {@link MenuEntry} with the given {@code displayName}, {@code value} and the
     * {@code actionType}.
     *
     * @param displayName The display name of the menu entry.
     * @param value The value for the menu entry.
     * @param actionType The action type for the menu entry.
     * @return A created menu entry.
     */
    MenuEntry create(String displayName, String value, ActionType actionType);
  }
}
