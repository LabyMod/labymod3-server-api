package net.labymod.serverapi.common.extension;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import net.labymod.serverapi.api.extension.ModificationExtension;

/** Default implementation of the {@link ModificationExtension}. */
public class DefaultModificationExtension implements ModificationExtension {

  private final String name;
  private final String identifier;

  /**
   * Initializes a new {@link DefaultModificationExtension} with the given {@code name} and {@code
   * identifier}.
   *
   * @param name The name of the modification.
   * @param identifier The identifier of the modification.
   */
  @AssistedInject
  private DefaultModificationExtension(
      @Assisted("name") String name, @Assisted("identifier") String identifier) {
    this.name = name;
    this.identifier = identifier;
  }

  /** {@inheritDoc} */
  @Override
  public String getName() {
    return this.name;
  }

  /** {@inheritDoc} */
  @Override
  public String getIdentifier() {
    return this.identifier;
  }
}
