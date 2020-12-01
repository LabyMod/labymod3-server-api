package net.labymod.serverapi.api.extension;

/**
 * Represents an addon, modification or package.
 *
 * @param <A> The name of the extension.
 * @param <B> The identifier of the extension.
 */
public interface Extension<A, B> {

  /**
   * Retrieves the name of the extension.
   *
   * @return The extension name.
   */
  A getName();

  /**
   * Retrieves the identifier of the extension.
   *
   * @return The extension identifier.
   */
  B getIdentifier();
}
