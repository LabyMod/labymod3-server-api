package net.labymod.serverapi.common.extension;

import net.labymod.serverapi.api.extension.ModificationExtension;

/** Default implementation of the {@link ModificationExtension.Factory}. */
public class DefaultModificationExtensionFactory implements ModificationExtension.Factory {

  /** {@inheritDoc} */
  @Override
  public ModificationExtension create(String name, String identifier) {
    return new DefaultModificationExtension(name, identifier);
  }
}
