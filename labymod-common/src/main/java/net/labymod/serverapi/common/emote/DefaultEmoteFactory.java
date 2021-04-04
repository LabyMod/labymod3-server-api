package net.labymod.serverapi.common.emote;

import java.util.UUID;
import net.labymod.serverapi.api.emote.Emote;

public class DefaultEmoteFactory implements Emote.Factory {

  @Override
  public Emote create(UUID npcUniqueId, int emoteId) {
    return new DefaultEmote(npcUniqueId, emoteId);
  }
}
