package net.labymod.serverapi.common.extension;

import net.labymod.serverapi.api.extension.PackageExtension;

public class DefaultPackageExtensionFactory implements PackageExtension.Factory {

  @Override
  public PackageExtension create(String name, String version) {
    return new DefaultPackageExtension(name, version);
  }
}
