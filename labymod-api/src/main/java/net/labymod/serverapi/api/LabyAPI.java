package net.labymod.serverapi.api;

public final class LabyAPI {

  private static LabyService service;

  /**
   * Initializes the {@link LabyService} associated with the currently running platform.
   *
   * @param service The laby service.
   */
  public static void initialize(LabyService service) {
    LabyAPI.service = service;
  }

  /**
   * Retrieves the laby service associated with the currently running platform.
   *
   * @return The laby service associated with the currently running platform.
   */
  public static LabyService getService() {
    return LabyAPI.service;
  }

  /**
   * Retrieves the debugger of the plugin.
   *
   * @return The plugin debugger.
   */
  public static LabyDebugger getDebugger() {
    return LabyAPI.service.getLabyDebugger();
  }
}
