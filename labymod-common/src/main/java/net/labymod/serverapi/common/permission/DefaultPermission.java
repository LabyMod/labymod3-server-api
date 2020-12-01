package net.labymod.serverapi.common.permission;

import net.labymod.serverapi.api.permission.Permissible;

/** A default implementation of the {@link Permissible}. */
public class DefaultPermission implements Permissible {

  private final String internalName;
  private final String name;
  private boolean enabled;

  /**
   * Initializes a new default permission with the given parameters.
   *
   * <p><b>Note:</b> The permission is default enabled.
   *
   * @param internalName The internal name of the permission.
   * @param name The name of the permission.
   */
  public DefaultPermission(String internalName, String name) {
    this(internalName, name, true);
  }

  /**
   * Initializes a new default permission with the given parameters.
   *
   * @param internalName The internal name of the permission.
   * @param name The name of the permission.
   * @param enabled {@code true} if the permission should be enabled, otherwise {@code false}.
   */
  public DefaultPermission(String internalName, String name, boolean enabled) {
    this.internalName = internalName;
    this.name = name;
    this.enabled = enabled;
  }

  /** {@inheritDoc} */
  @Override
  public String getInternalName() {
    return this.internalName;
  }

  /** {@inheritDoc} */
  @Override
  public String getName() {
    return this.name;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isEnabled() {
    return this.enabled;
  }

  /** {@inheritDoc} */
  @Override
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
}
