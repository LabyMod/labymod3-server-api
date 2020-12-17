package net.labymod.serverapi.common.serverinteraction.actionmenu;

import com.google.gson.JsonArray;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.UUID;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.serverinteraction.actionmenu.Menu;
import net.labymod.serverapi.api.serverinteraction.actionmenu.MenuEntry;

@Singleton
public class DefaultMenu implements Menu {

  private static final String USER_MENU_ACTIONS_CHANNEL = "user_menu_actions";

  private final PayloadCommunicator payloadCommunicator;
  private final JsonArray entries;

  @Inject
  private DefaultMenu(PayloadCommunicator payloadCommunicator) {
    this.payloadCommunicator = payloadCommunicator;
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
  public void send(UUID uniqueId) {
    this.payloadCommunicator.sendLabyModMessage(uniqueId, USER_MENU_ACTIONS_CHANNEL, this.entries);
  }
}
