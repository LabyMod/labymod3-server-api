package net.labymod.serverapi.api;

/** Represents the debugger for the plugin. */
public interface LabyDebugger {

  /**
   * Whether the plugin is in debug mode.
   *
   * @return {@code true} if the plugin is in debug mode, otherwise {@code false}.
   */
  boolean isDebug();

  /**
   * Changes whether the plugin is in debug mode.
   *
   * @param debug {@code true} if the plugin should be in debug mode, otherwise {@code false}.
   */
  void setDebug(boolean debug);
}
