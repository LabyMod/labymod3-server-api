package net.labymod.serverapi.common.serverinteraction.widgets;

import com.google.gson.JsonObject;
import net.labymod.serverapi.api.serverinteraction.widgets.WidgetScreenBuilder;
import net.labymod.serverapi.common.widgets.WidgetLayout;
import net.labymod.serverapi.common.widgets.WidgetScreen;
import net.labymod.serverapi.common.widgets.components.Widget;

public class DefaultWidgetScreenBuilder implements WidgetScreenBuilder {

  private final WidgetScreen screen;

  private DefaultWidgetScreenBuilder(int id) {
    this.screen = new WidgetScreen(id);
  }

  private DefaultWidgetScreenBuilder(JsonObject screenObject) {
    this.screen = WidgetScreen.from(screenObject);
  }

  /**
   * Creates a new {@link WidgetScreenBuilder} with the given {@code id}.
   *
   * @param id The identifier for the screen.
   * @return A new widget screen builder.
   */
  public static WidgetScreenBuilder create(final int id) {
    return new DefaultWidgetScreenBuilder(id);
  }

  /**
   * Creates a new {@link WidgetScreenBuilder} with the given {@code screenObject}.
   *
   * @param screenObject The screen as a {@link JsonObject}.
   * @return A new widget screen builder.
   */
  public static WidgetScreenBuilder create(final JsonObject screenObject) {
    return new DefaultWidgetScreenBuilder(screenObject);
  }

  /** {@inheritDoc} */
  @Override
  public WidgetScreenBuilder addWidget(Widget widget) {
    this.screen.addWidget(widget);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public WidgetScreenBuilder layout(WidgetLayout layout) {
    this.screen.setLayout(layout);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public WidgetScreen build() {
    return this.screen;
  }
}
