package net.labymod.serverapi.bukkit.player;

import com.google.inject.Singleton;
import net.labymod.serverapi.common.player.DefaultLabyModPlayerService;
import org.bukkit.entity.Player;

@Singleton
public class BukkitLabyModPlayerService extends DefaultLabyModPlayerService<Player> {}
