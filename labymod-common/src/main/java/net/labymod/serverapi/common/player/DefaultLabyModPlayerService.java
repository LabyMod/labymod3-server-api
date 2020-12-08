package net.labymod.serverapi.common.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.player.LabyModPlayerService;

public class DefaultLabyModPlayerService<T> implements LabyModPlayerService<T> {

  private final List<LabyModPlayer<T>> labyModPlayers;

  public DefaultLabyModPlayerService() {
    this.labyModPlayers = new ArrayList<>();
  }

  /** {@inheritDoc} */
  @Override
  public void registerPlayer(LabyModPlayer<T> player) {
    if (!this.getPlayer(player.getUniqueId()).isPresent()) {
      this.labyModPlayers.add(player);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void unregisterPlayer(LabyModPlayer<T> player) {
    this.labyModPlayers.remove(player);
  }

  /** {@inheritDoc} */
  @Override
  public boolean unregisterPlayerIf(Predicate<LabyModPlayer<T>> predicate) {
    return this.labyModPlayers.removeIf(predicate);
  }

  /** {@inheritDoc} */
  @Override
  public List<LabyModPlayer<T>> getPlayers() {
    return this.labyModPlayers;
  }

  /** {@inheritDoc} */
  @Override
  public Optional<LabyModPlayer<T>> getPlayer(String username) {
    return this.labyModPlayers.stream()
        .filter(labyModPlayer -> labyModPlayer.getUsername().equalsIgnoreCase(username))
        .findFirst();
  }

  /** {@inheritDoc} */
  @Override
  public Optional<LabyModPlayer<T>> getPlayer(UUID uniqueId) {
    return this.labyModPlayers.stream()
        .filter(labyModPlayer -> labyModPlayer.getUniqueId().equals(uniqueId))
        .findFirst();
  }
}
