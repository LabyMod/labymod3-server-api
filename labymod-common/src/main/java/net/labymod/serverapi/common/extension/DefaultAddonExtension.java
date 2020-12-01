package net.labymod.serverapi.common.extension;

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
  public DefaultAddonExtension(String name, UUID uniqueId) {
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
