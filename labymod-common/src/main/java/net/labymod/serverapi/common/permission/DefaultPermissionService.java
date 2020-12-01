package net.labymod.serverapi.common.permission;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.labymod.serverapi.api.permission.Permissible;
import net.labymod.serverapi.api.permission.Permissible.Factory;
import net.labymod.serverapi.api.permission.PermissionService;

/** A default implementation of the {@link PermissionService}. */
public class DefaultPermissionService implements PermissionService {

  private final List<Permissible> permissions;
  private final Permissible.Factory permissionFactory;

  /**
   * Initializes a new {@link DefaultPermissionService} with the given {@link Permissible.Factory}.
   *
   * @param permissionFactory The permission factory for creating permissions.
   */
  public DefaultPermissionService(Factory permissionFactory) {
    this.permissionFactory = permissionFactory;
    this.permissions = new ArrayList<>();
  }

  /** {@inheritDoc} */
  @Override
  public void registerPermission(String internalName, String name, boolean enabled) {
    if (this.shouldPermissionRegister(internalName)) {
      this.permissions.add(this.permissionFactory.create(internalName, name, enabled));
    }
  }

  /** {@inheritDoc} */
  @Override
  public void registerPermission(Permissible permissible) {
    this.permissions.add(permissible);
  }

  /** {@inheritDoc} */
  @Override
  public void unregisterPermission(String internalName) {
    this.permissions.removeIf(
        permissible -> permissible.getInternalName().equals(internalName.toUpperCase()));
  }

  /** {@inheritDoc} */
  @Override
  public void unregisterPermission(Permissible permissible) {
    this.permissions.removeIf(
        permissibleCheck ->
            permissibleCheck.getInternalName().equals(permissible.getInternalName()));
  }

  /** {@inheritDoc} */
  @Override
  public void unregisterAllPermissions() {
    this.permissions.clear();
  }

  /** {@inheritDoc} */
  @Override
  public void enablePermission(String internalName) {
    this.getPermission(internalName)
        .ifPresent(
            permissible -> {
              permissible.setEnabled(true);
            });
  }

  /** {@inheritDoc} */
  @Override
  public void disablePermission(String internalName) {
    this.getPermission(internalName)
        .ifPresent(
            permissible -> {
              permissible.setEnabled(false);
            });
  }

  /** {@inheritDoc} */
  @Override
  public JsonObject getPermissionsAsJson() {
    JsonObject permissions = new JsonObject();

    for (Permissible permission : this.permissions) {
      permissions.addProperty(permission.getInternalName(), permission.isEnabled());
    }

    return permissions;
  }

  /** {@inheritDoc} */
  @Override
  public Optional<Permissible> getPermission(String internalName) {
    Optional<Permissible> optional = Optional.empty();

    for (Permissible permission : this.permissions) {
      if (permission.getInternalName().equals(internalName.toUpperCase())) {
        optional = Optional.of(permission);
      }
    }

    return optional;
  }

  @Override
  public boolean shouldPermissionRegister(String internalName) {
    return this.permissions.stream()
        .noneMatch(permission -> permission.getInternalName().equals(internalName.toUpperCase()));
  }

  /** {@inheritDoc} */
  @Override
  public List<Permissible> getEnabledPermissions() {
    return this.permissions.stream().filter(Permissible::isEnabled).collect(Collectors.toList());
  }

  /** {@inheritDoc} */
  @Override
  public List<Permissible> getDisabledPermissions() {
    return this.permissions.stream()
        .filter(permission -> !permission.isEnabled())
        .collect(Collectors.toList());
  }

  /** {@inheritDoc} */
  @Override
  public List<Permissible> getPermissions() {
    return this.permissions;
  }
}
