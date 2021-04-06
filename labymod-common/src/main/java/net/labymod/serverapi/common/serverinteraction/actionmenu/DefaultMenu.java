package net.labymod.serverapi.common.serverinteraction.actionmenu;

import com.google.gson.JsonArray;
import net.labymod.serverapi.api.LabyService;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.player.LabyModPlayer;
import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.api.serverinteraction.actionmenu.Menu;
import net.labymod.serverapi.api.serverinteraction.actionmenu.MenuEntry;

import java.util.UUID;

public class DefaultMenu implements Menu {

  private static final String USER_MENU_ACTIONS_CHANNEL = "user_menu_actions";

  private final LabyModPlayerService<?> playerService;
  private final PayloadCommunicator payloadCommunicator;
  private final JsonArray entries;

  public DefaultMenu(LabyService service) {
    this.payloadCommunicator = service.getPayloadCommunicator();
    this.playerService = service.getLabyPlayerService();
    this.entries = new JsonArray();
  }

  /** {@inheritDoc} */
  @Override
  public Menu addEntry(MenuEntry entry) {
    this.entries.add(entry.asJsonObject());
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public Menu addEntries(MenuEntry... entries) {
    for (MenuEntry entry : entries) {
      this.entries.add(entry.asJsonObject());
    }
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public void transmit(UUID uniqueId) {
    this.payloadCommunicator.sendLabyModMessage(uniqueId, USER_MENU_ACTIONS_CHANNEL, this.entries);
  }

  @Override
  public void broadcastTransmit() {
    for (LabyModPlayer<?> player : this.playerService.getPlayers()) {
      this.transmit(player.getUniqueId());
    }
  }
}
