package net.labymod.serverapi.api.protocol.chunkcaching;

import net.labymod.serverapi.api.player.LabyModPlayer;

@FunctionalInterface
public interface ChunkHandle<T> {

  boolean handle(LabyModPlayer<T> player);



}
