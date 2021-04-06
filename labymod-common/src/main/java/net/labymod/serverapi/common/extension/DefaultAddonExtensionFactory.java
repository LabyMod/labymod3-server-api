package net.labymod.serverapi.common.extension;

import net.labymod.serverapi.api.extension.AddonExtension;

import java.util.UUID;

public class DefaultAddonExtensionFactory implements AddonExtension.Factory {

  @Override
  public AddonExtension create(String name, UUID uniqueId) {
    return new DefaultAddonExtension(name, uniqueId);
  }
}
