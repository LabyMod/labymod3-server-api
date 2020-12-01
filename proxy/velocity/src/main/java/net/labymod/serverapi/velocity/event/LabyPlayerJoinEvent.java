package net.labymod.serverapi.velocity.event;

import com.velocitypowered.api.proxy.Player;
import java.util.List;
import net.labymod.serverapi.api.extension.AddonExtension;
import net.labymod.serverapi.api.extension.ModificationExtension;
import net.labymod.serverapi.api.protocol.ChunkCachingProtocol;
import net.labymod.serverapi.api.protocol.ShadowProtocol;

public class LabyPlayerJoinEvent {

  private final Player player;
  private final List<AddonExtension> addonExtensions;
  private final List<ModificationExtension> modificationExtensions;
  private final String version;
  private final ChunkCachingProtocol chunkCachingProtocol;
  private final ShadowProtocol shadowProtocol;

  public LabyPlayerJoinEvent(Player player,
      List<AddonExtension> addonExtensions,
      List<ModificationExtension> modificationExtensions, String version,
      ChunkCachingProtocol chunkCachingProtocol,
      ShadowProtocol shadowProtocol) {
    this.player = player;
    this.addonExtensions = addonExtensions;
    this.modificationExtensions = modificationExtensions;
    this.version = version;
    this.chunkCachingProtocol = chunkCachingProtocol;
    this.shadowProtocol = shadowProtocol;
  }

  public Player getPlayer() {
    return player;
  }

  public List<AddonExtension> getAddonExtensions() {
    return addonExtensions;
  }

  public List<ModificationExtension> getModificationExtensions() {
    return modificationExtensions;
  }

  public String getVersion() {
    return version;
  }

  public ChunkCachingProtocol getChunkCachingProtocol() {
    return chunkCachingProtocol;
  }

  public ShadowProtocol getShadowProtocol() {
    return shadowProtocol;
  }
}
