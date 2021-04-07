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

  /**
   * Logs a message at the INFO level.
   *
   * @param message The message string to be logged.
   */
  void info(String message);

  /**
   * Logs an exception (throwable) at the INFO level with an accompanying message.
   *
   * @param message The message accompanying the exception.
   * @param throwable The exception (throwable) to be log.
   */
  void info(String message, Throwable throwable);

  /**
   * Logs a message at the WARN level.
   *
   * @param message The message string to be logged.
   */
  void warn(String message);

  /**
   * Logs an exception (throwable) at the WARN level with an accompanying message.
   *
   * @param message The message accompanying the exception.
   * @param throwable The exception (throwable) to be log.
   */
  void warn(String message, Throwable throwable);

  /**
   * Logs a message at the ERROR level.
   *
   * @param message The message string to be logged.
   */
  void error(String message);

  /**
   * Logs an exception (throwable) at the ERROR level with an accompanying message.
   *
   * @param message The message accompanying the exception.
   * @param throwable The exception (throwable) to be log.
   */
  void error(String message, Throwable throwable);
}
