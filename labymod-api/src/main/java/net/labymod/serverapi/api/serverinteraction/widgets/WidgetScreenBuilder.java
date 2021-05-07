package net.labymod.serverapi.api.serverinteraction.widgets;

import net.labymod.serverapi.common.widgets.WidgetLayout;
import net.labymod.serverapi.common.widgets.WidgetScreen;
import net.labymod.serverapi.common.widgets.components.Widget;
import net.labymod.serverapi.common.widgets.util.EnumScreenAction;

/** A builder for {@link WidgetScreen}. */
public interface WidgetScreenBuilder {

  /**
   * Adds a widget to the screen.
   *
   * @param widget The widget to add.
   * @return This builder, for chaining.
   */
  WidgetScreenBuilder addWidget(Widget widget);

  /**
   * Changes the layout of the wrapped inventory.<br>
   * <b>Note:</b> This is only available when using the screen action {@link
   * EnumScreenAction#WRAP_INVENTORY}
   *
   * @param layout The screen layout.
   * @return This builder, for chaining.
   */
  WidgetScreenBuilder layout(WidgetLayout layout);

  /**
   * The built screen.
   *
   * @return The built screen.
   */
  WidgetScreen build();
}
