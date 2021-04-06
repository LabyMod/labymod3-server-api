package net.labymod.serverapi.common.emote;

import net.labymod.serverapi.api.emote.Emote;

import java.util.UUID;

public class DefaultEmoteFactory implements Emote.Factory {

  @Override
  public Emote create(UUID npcUniqueId, int emoteId) {
    return new DefaultEmote(npcUniqueId, emoteId);
  }
}
