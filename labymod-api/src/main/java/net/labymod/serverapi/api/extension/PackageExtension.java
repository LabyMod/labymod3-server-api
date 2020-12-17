package net.labymod.serverapi.api.extension;

import com.google.inject.assistedinject.Assisted;

public interface PackageExtension extends Extension<String, String> {

  /** A factory for creating {@link PackageExtension}'s. */
  interface Factory {

    /**
     * Creates a new {@link PackageExtension} with the {@code name} and {@code version}.
     *
     * @param name The name of the package.
     * @param version The version of the package.
     * @return A created package extension.
     */
    PackageExtension create(@Assisted("name") String name, @Assisted("version") String version);
  }
}
