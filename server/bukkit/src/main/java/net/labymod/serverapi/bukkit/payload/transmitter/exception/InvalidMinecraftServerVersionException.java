package net.labymod.serverapi.bukkit.payload.transmitter.exception;

public class InvalidMinecraftServerVersionException extends RuntimeException {

  public InvalidMinecraftServerVersionException() {}

  public InvalidMinecraftServerVersionException(String message) {
    super(message);
  }

  public InvalidMinecraftServerVersionException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidMinecraftServerVersionException(Throwable cause) {
    super(cause);
  }

  public InvalidMinecraftServerVersionException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
