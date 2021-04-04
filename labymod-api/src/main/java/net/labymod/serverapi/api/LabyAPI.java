package net.labymod.serverapi.api;

public class LabyAPI {

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
    return service;
  }
}
