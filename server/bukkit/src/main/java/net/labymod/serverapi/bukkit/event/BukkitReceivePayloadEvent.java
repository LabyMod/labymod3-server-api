package net.labymod.serverapi.bukkit.event;

import java.util.UUID;

public class BukkitReceivePayloadEvent extends BukkitLabyModEvent {

  private final UUID uniqueId;
  private final String identifier;
  private final byte[] payload;

  public BukkitReceivePayloadEvent(UUID uniqueId, String identifier, byte[] payload) {
    this.uniqueId = uniqueId;
    this.identifier = identifier;
    this.payload = payload;
  }

  public UUID getUniqueId() {
    return uniqueId;
  }

  public String getIdentifier() {
    return identifier;
  }

  public byte[] getPayload() {
    return payload;
  }
}
