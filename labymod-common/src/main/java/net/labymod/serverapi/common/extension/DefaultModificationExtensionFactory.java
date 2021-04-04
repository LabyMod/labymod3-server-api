package net.labymod.serverapi.common.extension;

import net.labymod.serverapi.api.extension.ModificationExtension;

public class DefaultModificationExtensionFactory implements ModificationExtension.Factory {

  @Override
  public ModificationExtension create(String name, String identifier) {
    return new DefaultModificationExtension(name, identifier);
  }
}
