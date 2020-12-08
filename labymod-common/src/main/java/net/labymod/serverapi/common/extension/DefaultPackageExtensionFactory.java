package net.labymod.serverapi.common.extension;

import net.labymod.serverapi.api.extension.PackageExtension;

/** Default implementation of the {@link PackageExtension.Factory}. */
public class DefaultPackageExtensionFactory implements PackageExtension.Factory {

  private static final PackageExtension.Factory INSTANCE = new DefaultPackageExtensionFactory();

  private DefaultPackageExtensionFactory() {}

  public static PackageExtension.Factory getInstance() {
    return INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public PackageExtension create(String name, String version) {
    return new DefaultPackageExtension(name, version);
  }
}
