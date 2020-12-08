package net.labymod.serverapi.common.extension;

import java.util.UUID;
import net.labymod.serverapi.api.extension.AddonExtension;

/** Default implementation of the {@link AddonExtension.Factory}. */
public class DefaultAddonExtensionFactory implements AddonExtension.Factory {

  private static final AddonExtension.Factory INSTANCE = new DefaultAddonExtensionFactory();

  private DefaultAddonExtensionFactory() {}

  public static AddonExtension.Factory getInstance() {
    return INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public AddonExtension create(String name, UUID uniqueId) {
    return new DefaultAddonExtension(name, uniqueId);
  }
}
