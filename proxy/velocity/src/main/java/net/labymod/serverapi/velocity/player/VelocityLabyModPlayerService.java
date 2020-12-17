package net.labymod.serverapi.velocity.player;

import com.google.inject.Singleton;
import com.velocitypowered.api.proxy.Player;
import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.common.player.DefaultLabyModPlayerService;

@Singleton
public class VelocityLabyModPlayerService extends DefaultLabyModPlayerService<Player> {

}
