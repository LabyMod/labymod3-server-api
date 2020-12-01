package net.labymod.serverapi.common.emote;

import java.util.UUID;
import net.labymod.serverapi.api.emote.Emote;

/** Default implementation of the {@link Emote.Factory}. */
public class DefaultEmoteFactory implements Emote.Factory {

  /** {@inheritDoc} */
  @Override
  public Emote create(UUID npcUniqueId, int emoteId) {
    return new DefaultEmote(npcUniqueId, emoteId);
  }
}
