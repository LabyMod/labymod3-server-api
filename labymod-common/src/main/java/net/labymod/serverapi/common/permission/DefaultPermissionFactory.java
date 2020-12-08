package net.labymod.serverapi.common.permission;

import net.labymod.serverapi.api.permission.Permissible;
import net.labymod.serverapi.api.permission.Permissible.Factory;

/** A default implementation of the {@link Permissible.Factory}. */
public class DefaultPermissionFactory implements Permissible.Factory {

  private static final Permissible.Factory INSTANCE = new DefaultPermissionFactory();

  private DefaultPermissionFactory() {}

  public static Factory getInstance() {
    return INSTANCE;
  }

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
