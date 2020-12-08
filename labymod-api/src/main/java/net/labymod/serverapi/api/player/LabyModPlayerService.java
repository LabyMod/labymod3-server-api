package net.labymod.serverapi.api.player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public interface LabyModPlayerService<T> {

  // TODO: 07.12.2020 Still to be clarified with LabyStudio.
  int[] NON_LEGACY_SUPPORT_VERSION = new int[] {3, 7, 7};

  /**
   * Registers a LabyMod player.
   *
   * @param player The player to be registered.
   */
  void registerPlayer(LabyModPlayer<T> player);

  /**
   * Unregisters a LabyMod player.
   *
   * @param player The player to be unregistered.
   */
  void unregisterPlayer(LabyModPlayer<T> player);

  boolean unregisterPlayerIf(Predicate<LabyModPlayer<T>> predicate);

  /**
   * Retrieves a collection with all connected LabyMod players.
   *
   * @return A collection with all connected LabyMod players.
   */
  List<LabyModPlayer<T>> getPlayers();

  /**
   * Retrieves the player currently connected with LabyMod by their Minecraft username.
   *
   * @param username The username of a LabyMod player.
   * @return An optional with the player that can be empty.
   */
  Optional<LabyModPlayer<T>> getPlayer(String username);

  /**
   * Retrieves the player currently connected with LabyMod by their Minecraft unique identifier.
   *
   * @param uniqueId The unique identifier of a LabyMod player.
   * @return An optional with the player that can be empty.
   */
  Optional<LabyModPlayer<T>> getPlayer(UUID uniqueId);
}
