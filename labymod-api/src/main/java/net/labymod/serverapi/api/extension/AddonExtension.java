package net.labymod.serverapi.api.extension;

import java.util.UUID;

/** Represents an addon of the LabyMod. */
public interface AddonExtension extends Extension<String, UUID> {

  /** A factory for creating {@link AddonExtension}'s. */
  interface Factory {

    /**
     * Creates an new {@link AddonExtension} with the given {@code name} and {@code uniqueId}.
     *
     * @param name The name of the addon.
     * @param uniqueId The unique identifier of the addon.
     * @return A created addon extension.
     */
    AddonExtension create(String name, UUID uniqueId);
  }
}
