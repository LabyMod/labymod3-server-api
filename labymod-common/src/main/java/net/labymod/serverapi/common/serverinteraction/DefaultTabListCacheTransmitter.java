package net.labymod.serverapi.common.serverinteraction;

import com.google.gson.JsonObject;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.serverinteraction.TabListCacheTransmitter;

/** Default implementation of {@link TabListCacheTransmitter}. */
@Singleton
public class DefaultTabListCacheTransmitter implements TabListCacheTransmitter {

  private static final String TABLIST_CACHE_CHANNEL = "tablist_cache";

  private final PayloadCommunicator communicator;
  private boolean enabled;

  @Inject
  private DefaultTabListCacheTransmitter(PayloadCommunicator communicator) {
    this.communicator = communicator;
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
  public void transmit(LabyModPlayer<?> player) {
    JsonObject object = new JsonObject();
    object.addProperty("enabled", this.enabled);
    this.communicator.sendLabyModMessage(player.getUniqueId(), TABLIST_CACHE_CHANNEL, object);
  }
}
