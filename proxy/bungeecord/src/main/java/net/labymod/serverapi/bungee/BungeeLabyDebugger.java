package net.labymod.serverapi.bungee;

import net.labymod.serverapi.common.AbstractLabyDebugger;

public class BungeeLabyDebugger extends AbstractLabyDebugger {

  private static final String DEBUG_MESSAGE_FORMAT = "[LabyApi] %s";
  private final BungeeLabyModPlugin plugin;

  public BungeeLabyDebugger(BungeeLabyModPlugin plugin) {
    this.plugin = plugin;
  }

  /** {@inheritDoc} */
  @Override
  public void info(String message) {
    if (this.isDebug()) {
      this.plugin.getLogger().info(String.format(DEBUG_MESSAGE_FORMAT, message));
    }
  }

  /** {@inheritDoc} */
  @Override
  public void info(String message, Throwable throwable) {
    if (this.isDebug()) {
      this.plugin
          .getLogger()
          .info(String.format(DEBUG_MESSAGE_FORMAT + " (%s)", message, throwable.getMessage()));
    }
  }

  /** {@inheritDoc} */
  @Override
  public void warn(String message) {
    if (this.isDebug()) {
      this.plugin.getLogger().warning(String.format(DEBUG_MESSAGE_FORMAT, message));
    }
  }

  /** {@inheritDoc} */
  @Override
  public void warn(String message, Throwable throwable) {
    if (this.isDebug()) {
      this.plugin
          .getLogger()
          .warning(String.format(DEBUG_MESSAGE_FORMAT + " (%s)", message, throwable.getMessage()));
    }
  }

  /** {@inheritDoc} */
  @Override
  public void error(String message) {
    if (this.isDebug()) {
      this.plugin.getLogger().severe(String.format(DEBUG_MESSAGE_FORMAT, message));
    }
  }

  /** {@inheritDoc} */
  @Override
  public void error(String message, Throwable throwable) {
    if (this.isDebug()) {
      this.plugin
          .getLogger()
          .severe(String.format(DEBUG_MESSAGE_FORMAT + " (%s)", message, throwable.getMessage()));
    }
  }
}
