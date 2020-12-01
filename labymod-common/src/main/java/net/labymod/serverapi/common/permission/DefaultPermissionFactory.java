package net.labymod.serverapi.common.permission;

import net.labymod.serverapi.api.permission.Permissible;

/** A default implementation of the {@link Permissible.Factory}. */
public class DefaultPermissionFactory implements Permissible.Factory {

  /** {@inheritDoc} */
  @Override
  public Permissible create(String internalName, String name) {
    return new DefaultPermission(name, internalName);
  }

  /** {@inheritDoc} */
  @Override
  public Permissible create(String internalName, String name, boolean enabled) {
    return new DefaultPermission(internalName, name, enabled);
  }
}
