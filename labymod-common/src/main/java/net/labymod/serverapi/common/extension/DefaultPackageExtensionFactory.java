package net.labymod.serverapi.common.extension;

import net.labymod.serverapi.api.extension.PackageExtension;

/**
 * Default implementation of the {@link PackageExtension.Factory}.
 */
public class DefaultPackageExtensionFactory implements PackageExtension.Factory {

  /** {@inheritDoc} */
  @Override
  public PackageExtension create(String name, String version) {
    return new DefaultPackageExtension(name, version);
  }
}
