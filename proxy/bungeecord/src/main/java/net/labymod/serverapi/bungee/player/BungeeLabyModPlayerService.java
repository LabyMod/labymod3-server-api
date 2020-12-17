package net.labymod.serverapi.bungee.player;

import com.google.inject.Singleton;
import net.labymod.serverapi.common.player.DefaultLabyModPlayerService;
import net.md_5.bungee.api.connection.ProxiedPlayer;

@Singleton
public class BungeeLabyModPlayerService extends DefaultLabyModPlayerService<ProxiedPlayer> {}
