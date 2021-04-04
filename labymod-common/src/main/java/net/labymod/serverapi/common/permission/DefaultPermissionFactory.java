package net.labymod.serverapi.common.permission;

import net.labymod.serverapi.api.permission.Permissible;

public class DefaultPermissionFactory implements Permissible.Factory {

  @Override
  public Permissible create(String internalName, String name) {
    return new DefaultPermission(internalName, name);
  }

  @Override
  public Permissible create(String internalName, String name, boolean enabled) {
    return new DefaultPermission(internalName, name, enabled);
  }
}
