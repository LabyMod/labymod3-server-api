package net.labymod.serverapi.api.extension;

/** Represents a modification of the forge mod loader. */
public interface ModificationExtension extends Extension<String, String> {

  /** A factory for creating {@link ModificationExtension}'s. */
  interface Factory {

    /**
     * Creates a new {@link ModificationExtension}
     *
     * @param name The name of the modification.
     * @param identifier The identifier of the modification.
     * @return A created modification extension.
     */
    ModificationExtension create(String name, String identifier);
  }
}
