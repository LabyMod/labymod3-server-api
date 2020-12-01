package net.labymod.serverapi.common.extension;

import net.labymod.serverapi.api.extension.PackageExtension;

/** Default implementation of the {@link PackageExtension}. */
public class DefaultPackageExtension implements PackageExtension {

  private final String name;
  private final String version;

  /**
   * Initializes a new {@link DefaultPackageExtension} with the {@code name} and {@code version}.
   *
   * @param name The name of the package.
   * @param version The version of the package.
   */
  public DefaultPackageExtension(String name, String version) {
    this.name = name;
    this.version = version;
  }

  /** {@inheritDoc} */
  @Override
  public String getName() {
    return this.name;
  }

  /** {@inheritDoc} */
  @Override
  public String getIdentifier() {
    return this.version;
  }
}
