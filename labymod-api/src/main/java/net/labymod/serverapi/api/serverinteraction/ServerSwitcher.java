package net.labymod.serverapi.api.serverinteraction;

import java.util.UUID;

/**
 * Represents the server switch packet.
 *
 * <p><b>Overview:</b>
 *
 * <p>With out server switch packet you can send LabyMod users between different servers. The server
 * can request a user to directly connect to a different IP address (or domain name). This can be
 * used to create multi-server-networks. As this does not need any extra proxy software and
 * hardware, it is generally cheaper for a network to connect players directly to the server instead
 * of using an intermediate software like BungeeCord.
 */
public interface ServerSwitcher {

  /**
   * Sends a player to another network or server.
   *
   * @param uniqueId The unique identifier of a player which should be sent to another
   *     network/server.
   * @param title The title of the warning.
   * @param address The address of the network / server where the player should be sent to.
   * @param preview {@code true} if information about the network / server should be displayed,
   *     otherwise {@code false}.
   */
  void sendPlayerToServer(UUID uniqueId, String title, String address, boolean preview);
}
