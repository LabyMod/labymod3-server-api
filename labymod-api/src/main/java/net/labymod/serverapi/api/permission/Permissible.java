package net.labymod.serverapi.api.permission;

/** Represents a Labymod permission. */
public interface Permissible {

  /**
   * Retrieves the internal name of this permission.
   *
   * <p><b>Note:</b> The internal name is sent to the client.
   *
   * @return The permission's internal name.
   */
  String getInternalName();

  /**
   * Retrieves the name of this permission.
   *
   * @return The permission's name.
   */
  String getName();

  /**
   * Whether the permission is enabled.
   *
   * @return {@code true} if the permission is enabled, otherwise {@code false}.
   */
  boolean isEnabled();

  /**
   * Changes whether the permission is enabled.
   *
   * @param enabled {@code true} if the permission should be enabled, otherwise {@code false}.
   */
  void setEnabled(boolean enabled);

  /** A factory for creating {@link Permissible}'s. */
  interface Factory {

    /**
     * Creates a new {@link Permissible} with the given name.
     *
     * <p><b>Note:</b> The created permission is default enabled.
     *
     * @param internalName The internal name of the permission.
     * @param name The name of the permission to be created.
     * @return A created permission.
     */
    Permissible create(String internalName, String name);

    /**
     * Creates a new {@link Permissible} with the given name and the enable state.
     *
     * @param internalName The internal name of the permission.
     * @param name The name of the permission to be created.
     * @param enabled {@code true} if the permission should be enabled, otherwise {@code false}.
     * @return A created permission.
     */
    Permissible create(String internalName, String name, boolean enabled);
  }
}
