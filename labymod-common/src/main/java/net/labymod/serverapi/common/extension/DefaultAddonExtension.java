package net.labymod.serverapi.common.extension;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import java.util.UUID;
import net.labymod.serverapi.api.extension.AddonExtension;

/** Default implementation of the {@link AddonExtension}. */
public class DefaultAddonExtension implements AddonExtension {

  private final String name;
  private final UUID uniqueId;

  /**
   * Initializes a new {@link DefaultAddonExtension} with the given {@code name} and {@code
   * uniqueId}.
   *
   * @param name the name of the addon.
   * @param uniqueId The unique identifier of the addon.
   */
  @AssistedInject
  private DefaultAddonExtension(@Assisted String name, @Assisted UUID uniqueId) {
    this.name = name;
    this.uniqueId = uniqueId;
  }

  /** {@inheritDoc} */
  @Override
  public String getName() {
    return this.name;
  }

  /** {@inheritDoc} */
  @Override
  public UUID getIdentifier() {
    return this.uniqueId;
  }
}
