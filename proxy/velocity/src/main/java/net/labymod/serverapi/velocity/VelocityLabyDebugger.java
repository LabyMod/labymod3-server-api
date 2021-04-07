package net.labymod.serverapi.velocity;

import net.labymod.serverapi.common.AbstractLabyDebugger;
import org.slf4j.Logger;

public class VelocityLabyDebugger extends AbstractLabyDebugger {

  private static final String DEBUGGER_FORMAT = "[LabyApi] {}";
  private final Logger logger;

  public VelocityLabyDebugger(Logger logger) {
    this.logger = logger;
  }

  /** {@inheritDoc} */
  @Override
  public void info(String message) {
    if (this.isDebug()) {
      this.logger.info(DEBUGGER_FORMAT, message);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void info(String message, Throwable throwable) {
    if (this.isDebug()) {
      this.logger.info(DEBUGGER_FORMAT, message, throwable);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void warn(String message) {
    if (this.isDebug()) {
      this.logger.warn(DEBUGGER_FORMAT, message);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void warn(String message, Throwable throwable) {
    if (this.isDebug()) {
      this.logger.warn(DEBUGGER_FORMAT, message, throwable);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void error(String message) {
    if (this.isDebug()) {
      this.logger.error(DEBUGGER_FORMAT, message);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void error(String message, Throwable throwable) {
    if (this.isDebug()) {
      this.logger.error(DEBUGGER_FORMAT, message, throwable);
    }
  }
}
