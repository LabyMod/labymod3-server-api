package net.labymod.serverapi.common.extension;

import com.google.gson.JsonObject;
import java.util.Collections;
import java.util.List;
import net.labymod.serverapi.api.extension.ExtensionCollector;
import net.labymod.serverapi.api.extension.PackageExtension;

public class DefaultPackageExtensionCollector implements ExtensionCollector<PackageExtension> {

  private static final ExtensionCollector<PackageExtension> INSTANCE =
      new DefaultPackageExtensionCollector(DefaultPackageExtensionFactory.getInstance());

  private final PackageExtension.Factory packageExtensionFactory;

  private DefaultPackageExtensionCollector(PackageExtension.Factory packageExtensionFactory) {
    this.packageExtensionFactory = packageExtensionFactory;
  }

  public static ExtensionCollector<PackageExtension> getInstance() {
    return INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public List<PackageExtension> collect(JsonObject object) {
    // TODO: 29.11.2020 Implementation
    return Collections.emptyList();
  }
}
