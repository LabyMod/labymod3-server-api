package net.labymod.serverapi.common.serverinteraction;

import com.google.gson.JsonObject;
import net.labymod.serverapi.api.LabyService;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.api.serverinteraction.TabListCache;

import java.util.UUID;

/** Default implementation of {@link TabListCache}. */
public class DefaultTabListCache implements TabListCache {

  private static final String TABLIST_CACHE_CHANNEL = "tablist_cache";

  private final LabyModPlayerService<?> playerService;
  private final PayloadCommunicator communicator;
  private boolean enabled;

  public DefaultTabListCache(LabyService service) {
    this.communicator = service.getPayloadCommunicator();
    this.playerService = service.getLabyPlayerService();
    this.enabled = true;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isEnabled() {
    return this.enabled;
  }

  /** {@inheritDoc} */
  @Override
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  /** {@inheritDoc} */
  @Override
  public void transmit(UUID uniqueId) {
    JsonObject object = new JsonObject();
    object.addProperty("enabled", this.enabled);
    this.communicator.sendLabyModMessage(uniqueId, TABLIST_CACHE_CHANNEL, object);
  }

  /** {@inheritDoc} */
  @Override
  public void transmitBroadcast() {
    for (LabyModPlayer<?> player : this.playerService.getPlayers()) {
      this.transmit(player.getUniqueId());
    }
  }
}
