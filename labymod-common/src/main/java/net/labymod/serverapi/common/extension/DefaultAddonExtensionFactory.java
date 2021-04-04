package net.labymod.serverapi.common.extension;

import java.util.UUID;
import net.labymod.serverapi.api.extension.AddonExtension;

public class DefaultAddonExtensionFactory implements AddonExtension.Factory {

  @Override
  public AddonExtension create(String name, UUID uniqueId) {
    return new DefaultAddonExtension(name, uniqueId);
  }
}
