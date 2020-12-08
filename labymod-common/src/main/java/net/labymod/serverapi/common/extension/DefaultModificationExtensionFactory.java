package net.labymod.serverapi.common.extension;

import net.labymod.serverapi.api.extension.ModificationExtension;

/** Default implementation of the {@link ModificationExtension.Factory}. */
public class DefaultModificationExtensionFactory implements ModificationExtension.Factory {

  private static final ModificationExtension.Factory INSTANCE =
      new DefaultModificationExtensionFactory();

  private DefaultModificationExtensionFactory() {}

  public static ModificationExtension.Factory getInstance() {
    return INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public ModificationExtension create(String name, String identifier) {
    return new DefaultModificationExtension(name, identifier);
  }
}
