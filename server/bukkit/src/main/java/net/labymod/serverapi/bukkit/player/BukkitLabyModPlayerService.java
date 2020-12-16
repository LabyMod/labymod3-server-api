package net.labymod.serverapi.bukkit.player;

import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.common.player.DefaultLabyModPlayerService;
import org.bukkit.entity.Player;

public class BukkitLabyModPlayerService extends DefaultLabyModPlayerService<Player> {

  private static final LabyModPlayerService<Player> INSTANCE = new BukkitLabyModPlayerService();

  private BukkitLabyModPlayerService() {}

  public static LabyModPlayerService<Player> getInstance() {
    return INSTANCE;
  }
}
