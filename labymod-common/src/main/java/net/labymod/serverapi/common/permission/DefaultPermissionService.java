package net.labymod.serverapi.common.permission;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.permission.Permissible;
import net.labymod.serverapi.api.permission.PermissionService;

/** A default implementation of the {@link PermissionService}. */
public class DefaultPermissionService implements PermissionService {

  private static final String PERMISSIONS_KEY = "PERMISSIONS";

  private final List<Permissible> permissions;
  private final PayloadCommunicator payloadCommunicator;
  private final Permissible.Factory permissionFactory;

  /**
   * Initializes a new {@link DefaultPermissionService} with the given {@link Permissible.Factory}.
   *
   * @param payloadCommunicator The payload communicator.
   * @param permissionFactory The permission factory for creating permissions.
   */
  public DefaultPermissionService(
      PayloadCommunicator payloadCommunicator, Permissible.Factory permissionFactory) {
    this.payloadCommunicator = payloadCommunicator;
    this.permissionFactory = permissionFactory;
    this.permissions = new ArrayList<>();
    this.loadDefaultPermissions();
  }

  private void loadDefaultPermissions() {
    this.registerPermission("IMPROVED_LAVA", false);
    this.registerPermission("CROSSHAIR_SYNC", false);
    this.registerPermission("REFILL_FIX", false);
    this.registerPermission("GUI_ALL", true);
    this.registerPermission("GUI_POTION_EFFECTS", true);
    this.registerPermission("GUI_ARMOR_HUD", true);
    this.registerPermission("GUI_ITEM_HUD", true);
    this.registerPermission("BLOCKBUILD", true);
    this.registerPermission("TAGS", true);
    this.registerPermission("CHAT", true);
    this.registerPermission("ANIMATIONS", true);
    this.registerPermission("SATURATION_BAR", true);
  }

  /** {@inheritDoc} */
  @Override
  public void registerPermission(String internalName, boolean enabled) {
    this.registerPermission(internalName, internalName.toLowerCase(), enabled);
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
  public PermissionService enablePermission(String internalName) {
    this.getPermission(internalName)
        .ifPresent(
            permissible -> {
              permissible.setEnabled(true);
            });
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public PermissionService disablePermission(String internalName) {
    this.getPermission(internalName)
        .ifPresent(
            permissible -> {
              permissible.setEnabled(false);
            });
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public void sendPermissions(UUID receiverUniqueId) {
    this.payloadCommunicator.sendLabyModMessage(
        receiverUniqueId, PERMISSIONS_KEY, this.getPermissionsAsJson());
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

  /** {@inheritDoc} */
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
