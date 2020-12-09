package net.labymod.serverapi.bungee.player;

import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.common.player.DefaultLabyModPlayerService;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeLabyModPlayerService extends DefaultLabyModPlayerService<ProxiedPlayer> {

  private static final LabyModPlayerService<ProxiedPlayer> INSTANCE =
      new BungeeLabyModPlayerService();

  private BungeeLabyModPlayerService() {}

  public static LabyModPlayerService<ProxiedPlayer> getInstance() {
    return INSTANCE;
  }
}
