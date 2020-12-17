package net.labymod.serverapi.common.extension;

import com.google.gson.JsonObject;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.Collections;
import java.util.List;
import net.labymod.serverapi.api.extension.ExtensionCollector;
import net.labymod.serverapi.api.extension.PackageExtension;

@Singleton
public class DefaultPackageExtensionCollector implements ExtensionCollector<PackageExtension> {

  private final PackageExtension.Factory packageExtensionFactory;

  @Inject
  private DefaultPackageExtensionCollector(PackageExtension.Factory packageExtensionFactory) {
    this.packageExtensionFactory = packageExtensionFactory;
  }

  /** {@inheritDoc} */
  @Override
  public List<PackageExtension> collect(JsonObject object) {
    // TODO: 29.11.2020 Implementation
    return Collections.emptyList();
  }
}
