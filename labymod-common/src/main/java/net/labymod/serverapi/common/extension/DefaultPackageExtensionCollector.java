package net.labymod.serverapi.common.extension;

import com.google.gson.JsonObject;
import net.labymod.serverapi.api.extension.ExtensionCollector;
import net.labymod.serverapi.api.extension.PackageExtension;

import java.util.Collections;
import java.util.List;

public class DefaultPackageExtensionCollector implements ExtensionCollector<PackageExtension> {

  private final PackageExtension.Factory packageExtensionFactory;

  public DefaultPackageExtensionCollector() {
    this.packageExtensionFactory = new DefaultPackageExtensionFactory();
  }

  /** {@inheritDoc} */
  @Override
  public List<PackageExtension> collect(JsonObject object) {
    // TODO: 29.11.2020 Implementation
    return Collections.emptyList();
  }
}
