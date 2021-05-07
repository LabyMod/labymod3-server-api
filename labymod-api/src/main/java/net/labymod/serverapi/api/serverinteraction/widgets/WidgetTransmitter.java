package net.labymod.serverapi.api.serverinteraction.widgets;

import com.google.gson.JsonElement;
import java.util.UUID;
import net.labymod.serverapi.common.widgets.WidgetScreen;
import net.labymod.serverapi.common.widgets.util.EnumScreenAction;

/**
 * Represents the screen packet.
 *
 * <p><b>Overview:</b>
 *
 * <p>With this feature it is possible to create a custom user interface. We provide a collection of
 * GUI components you can use.
 */
public interface WidgetTransmitter {

  /**
   * Transmits the widget screen with the action {@link EnumScreenAction#OPEN} to the client with
   * the specified {@code uniqueId}.
   *
   * @param uniqueId The unique identifier of the receiver.
   * @param screen The screen to be transmitted.
   */
  default void transmitOpenScreen(UUID uniqueId, WidgetScreen screen) {
    this.transmit(uniqueId, screen.toJsonObject(EnumScreenAction.OPEN));
  }

  /**
   * Transmits the widget screen with the action {@link EnumScreenAction#CLOSE} to the client with
   * the specified {@code uniqueId}.
   *
   * @param uniqueId The unique identifier of the receiver.
   * @param screen The screen to be transmitted.
   */
  default void transmitCloseScreen(UUID uniqueId, WidgetScreen screen) {
    this.transmit(uniqueId, screen.toJsonObject(EnumScreenAction.CLOSE));
  }

  /**
   * Transmits the widget screen with the action {@link EnumScreenAction#UPDATE} to the client with
   * the specified {@code uniqueId}.
   *
   * @param uniqueId The unique identifier of the receiver.
   * @param screen The screen to be transmitted.
   */
  default void transmitUpdateScreen(UUID uniqueId, WidgetScreen screen) {
    this.transmit(uniqueId, screen.toJsonObject(EnumScreenAction.UPDATE));
  }

  /**
   * Transmits the widget screen with the action {@link EnumScreenAction#WRAP_INVENTORY} to the
   * client with the specified {@code uniqueId}.
   *
   * @param uniqueId The unique identifier of the receiver.
   * @param screen The screen to be transmitted.
   */
  default void transmitWrapInventoryScreen(UUID uniqueId, WidgetScreen screen) {
    this.transmit(uniqueId, screen.toJsonObject(EnumScreenAction.WRAP_INVENTORY));
  }

  /**
   * Transmits the widget screen to the client with the specified {@code uniqueId}.
   *
   * @param uniqueId The unique identifier of the receiver.
   * @param screen The screen to be transmitted.
   * @param screenAction The action to be transmitted.
   */
  default void transmit(UUID uniqueId, WidgetScreen screen, EnumScreenAction screenAction) {
    this.transmit(uniqueId, screen.toJsonObject(screenAction));
  }

  /**
   * Transmits the widget screen to the client with the specified {@code uniqueId}.
   *
   * @param uniqueId The unique identifier of the receiver.
   * @param screenElement The screen element to be transmitted.
   */
  void transmit(UUID uniqueId, JsonElement screenElement);

  /**
   * Transmits the widget screen with the action {@link EnumScreenAction#OPEN} to all online laby
   * users.
   *
   * @param screen The screen to be transmitted.
   */
  default void broadcastTransmitOpenScreen(WidgetScreen screen) {
    this.broadcastTransmit(screen.toJsonObject(EnumScreenAction.OPEN));
  }

  /**
   * Transmits the widget screen with the action {@link EnumScreenAction#CLOSE} to all online laby
   * users.
   *
   * @param screen The screen to be transmitted.
   */
  default void broadcastTransmitCloseScreen(WidgetScreen screen) {
    this.broadcastTransmit(screen.toJsonObject(EnumScreenAction.CLOSE));
  }

  /**
   * Transmits the widget screen with the action {@link EnumScreenAction#UPDATE} to all online laby
   * users.
   *
   * @param screen The screen to be transmitted.
   */
  default void broadcastTransmitUpdateScreen(WidgetScreen screen) {
    this.broadcastTransmit(screen.toJsonObject(EnumScreenAction.UPDATE));
  }

  /**
   * Transmits the widget screen with the action {@link EnumScreenAction#WRAP_INVENTORY} to all
   * online laby users.
   *
   * @param screen The screen to be transmitted.
   */
  default void broadcastTransmitWrapInventoryScreen(WidgetScreen screen) {
    this.broadcastTransmit(screen.toJsonObject(EnumScreenAction.WRAP_INVENTORY));
  }

  /**
   * Transmits the widget screen to all online laby users.
   *
   * @param screen The screen to be transmitted.
   * @param screenAction The action to be transmitted.
   */
  default void broadcastTransmit(WidgetScreen screen, EnumScreenAction screenAction) {
    this.broadcastTransmit(screen.toJsonObject(screenAction));
  }

  /**
   * Transmits the widget screen to all online laby users.
   *
   * @param screenElement The screen element to be transmitted.
   */
  void broadcastTransmit(JsonElement screenElement);
}
