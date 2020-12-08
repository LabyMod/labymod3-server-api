package net.labymod.serverapi.velocity.player;

import com.velocitypowered.api.proxy.Player;
import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.common.player.DefaultLabyModPlayerService;

public class VelocityLabyModPlayerService extends DefaultLabyModPlayerService<Player> {

  private static final LabyModPlayerService<Player> INSTANCE = new VelocityLabyModPlayerService();

  private VelocityLabyModPlayerService() {}

  public static LabyModPlayerService<Player> getInstance() {
    return INSTANCE;
  }
}
