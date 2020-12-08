package net.labymod.serverapi.api.permission;

import com.google.gson.JsonObject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PermissionService {

  /**
   * Register a new permission with the given name and the enabled state.
   *
   * @param internalName The internal name of the permission.
   * @param enabled {@code true} if the permission should be enabled, otherwise {@code false}.
   */
  void registerPermission(String internalName, boolean enabled);

  /**
   * Registers a new permission with the given name and the enabled state.
   *
   * @param internalName The internal name of the permission.
   * @param name The name of the permission to be registered.
   * @param enabled {@code true} if the permission should be enabled, otherwise {@code false}.
   */
  void registerPermission(String internalName, String name, boolean enabled);

  /**
   * Registers a new permission.
   *
   * @param permissible The permission to be registered.
   */
  void registerPermission(Permissible permissible);

  /**
   * Unregisters a permission with the given name.
   *
   * @param internalName The internal name of the permission to be unregistered.
   */
  void unregisterPermission(String internalName);

  /**
   * Unregisters a permission.
   *
   * @param permissible The permission to be unregistered.
   */
  void unregisterPermission(Permissible permissible);

  /** Unregisters all permissions. */
  void unregisterAllPermissions();

  /**
   * Enables a permission with the given internal name.
   *
   * @param internalName The permission's internal name.
   * @return This object for chaining.
   */
  PermissionService enablePermission(String internalName);

  /**
   * Disables a permission with the given internal name.
   *
   * @param internalName The permission's internal name.
   * @return This object for chaining.
   */
  PermissionService disablePermission(String internalName);

  /**
   * Sends all registered permissions to the unique identifier.
   *
   * @param receiverUniqueId The receiver of the permissions.
   */
  void sendPermissions(UUID receiverUniqueId);

  /**
   * Retrieves the registered permission as a {@link JsonObject}.
   *
   * @return The registered permission as a json object.
   */
  JsonObject getPermissionsAsJson();

  /**
   * Retrieves a permission with the given internal name.
   *
   * @param internalName The internal name of a permission.
   * @return An optional permission.
   */
  Optional<Permissible> getPermission(String internalName);

  /**
   * Whether the permission should be registered.
   *
   * @param internalName The internal name of the permission that should be registered.
   * @return {@code true} if the permission can be registered, otherwise {@code false}.
   */
  boolean shouldPermissionRegister(String internalName);

  /**
   * Retrieves a collection with all enabled permissions.
   *
   * @return A collection with all enabled permissions.
   */
  List<Permissible> getEnabledPermissions();

  /**
   * Retrieves a collection with all disabled permissions.
   *
   * @return A collection with all disabled permissions.
   */
  List<Permissible> getDisabledPermissions();

  /**
   * Retrieves a collection with all registered permissions.
   *
   * @return A collection with all registered permissions.
   */
  List<Permissible> getPermissions();
}
