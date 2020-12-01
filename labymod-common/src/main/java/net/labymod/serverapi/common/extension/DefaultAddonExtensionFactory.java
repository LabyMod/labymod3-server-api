package net.labymod.serverapi.common.extension;

import java.util.UUID;
import net.labymod.serverapi.api.extension.AddonExtension;

/** Default implementation of the {@link AddonExtension.Factory}. */
public class DefaultAddonExtensionFactory implements AddonExtension.Factory {

  /** {@inheritDoc} */
  @Override
  public AddonExtension create(String name, UUID uniqueId) {
    return new DefaultAddonExtension(name, uniqueId);
  }
}
