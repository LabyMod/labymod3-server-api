package net.labymod.serverapi.common;

import net.labymod.serverapi.api.LabyDebugger;

public abstract class AbstractLabyDebugger implements LabyDebugger {

  private boolean debug;

  /** {@inheritDoc} */
  @Override
  public boolean isDebug() {
    return this.debug;
  }

  /** {@inheritDoc} */
  @Override
  public void setDebug(boolean debug) {
    this.debug = debug;
  }
}
