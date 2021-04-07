package net.labymod.serverapi.api.player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * Represents a player service for the LabyMod.
 *
 * @param <T> The player type of the specified platform.
 */
public interface LabyModPlayerService<T> {

  int[] NON_LEGACY_SUPPORT_VERSION = new int[] {3, 8, 0};

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

  /**
   * Unregisters a LabyMod player that satisfy the given {@code predicate.}
   *
   * @param predicate A predicate which returns {@code true} for player to be removed.
   * @return {@code true} if any players were removed, otherwise {@code false}.
   */
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
