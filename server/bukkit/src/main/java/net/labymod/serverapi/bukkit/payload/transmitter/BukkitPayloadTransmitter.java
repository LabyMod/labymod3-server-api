package net.labymod.serverapi.bukkit.payload.transmitter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import net.labymod.serverapi.bukkit.payload.transmitter.exception.InvalidMinecraftServerVersionException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class BukkitPayloadTransmitter {

  private static final BukkitPayloadTransmitter INSTANCE = new BukkitPayloadTransmitter();

  private String version;

  /** Retrieves the `getHandle()` method. */
  private Method getHandleMethod;
  /** Retrieves the player connection field. */
  private Field playerConnectionField;
  /** Retrieves the send packet method */
  private Method sendPacketMethod;

  private Method wrappedBufferMethod;

  private Constructor<?> packetPlayOutCustomPayloadConstructor;

  // Minecraft 1.8 - 1.12.2 Support
  private Class<?> packetDataSerializerClass;
  private Constructor<?> packetDataSerializerConstructor;

  // Minecraft 1.13+ Support
  private Class<?> minecraftKeyClass;
  private Constructor<?> minecraftKeyConstructor;

  private BukkitPayloadTransmitter() {
    this.initialize();
  }

  public static void transmitPayload(Player player, String channelIdentifier, byte[] payload) {
    INSTANCE.sendPayload(player, channelIdentifier, payload);
  }

  private void initialize() {
    String packageName = Bukkit.getServer().getClass().getPackage().getName();
    String[] packages = packageName.split("\\.");

    if (packages.length > 0) {
      String serverVersion = packages[packages.length - 1];
      if (!serverVersion.startsWith("v")) {
        throw new InvalidMinecraftServerVersionException(
            "The version for the running Minecraft server not found, suffix got: " + serverVersion);
      }

      this.version = serverVersion;

      Class<?> craftPlayerClass = this.getOBCClass("entity.CraftPlayer");
      Class<?> entityPlayerClass = this.getNMSClass("EntityPlayer");
      Class<?> playerConnectionClass = this.getNMSClass("PlayerConnection");
      Class<?> packetPlayOutCustomPayloadClass = this.getNMSClass("PacketPlayOutCustomPayload");

      this.packetPlayOutCustomPayloadConstructor =
          this.getConstructor(packetPlayOutCustomPayloadClass, String.class, byte[].class);

      if (this.packetPlayOutCustomPayloadConstructor == null) {

        this.packetDataSerializerClass = this.getNMSClass("PacketDataSerializer");
        Class<?> byteBufClass = this.getClass("io.netty.buffer.ByteBuf");

        this.packetDataSerializerConstructor =
            this.getConstructor(this.packetDataSerializerClass, true, byteBufClass);

        Class<?> unpooledClass = this.getClass("io.netty.buffer.Unpooled");

        this.wrappedBufferMethod = this.getMethod(unpooledClass, "wrappedBuffer", byte[].class);

        this.packetPlayOutCustomPayloadConstructor =
            this.getConstructor(
                packetPlayOutCustomPayloadClass, String.class, this.packetDataSerializerClass);

        if (this.packetPlayOutCustomPayloadConstructor == null) {

          this.minecraftKeyClass = this.getNMSClass("MinecraftKey");
          this.minecraftKeyConstructor =
              this.getConstructor(minecraftKeyClass, String.class, String.class);

          this.packetPlayOutCustomPayloadConstructor =
              this.getConstructor(
                  packetPlayOutCustomPayloadClass,
                  this.minecraftKeyClass,
                  this.packetDataSerializerClass);
        }
      }

      this.getHandleMethod = this.getMethod(craftPlayerClass, "getHandle");
      this.playerConnectionField = this.getField(entityPlayerClass, "playerConnection");
      this.sendPacketMethod = this.getMethod(playerConnectionClass, "sendPacket");
    }
  }

  public void sendPayload(Player player, String channelIdentifier, byte[] payload) {
    try {

      if (channelIdentifier.contains(":")) {
        String[] identifiers = channelIdentifier.split(":");

        if (identifiers.length == 2) {
          if (this.minecraftKeyClass != null) {
            // Sends 1.13 + payload messages
            this.sendModernPayload(player, identifiers[0], identifiers[1], payload);
          }
        } else {
          throw new RuntimeException(
              String.format("Invalid payload channel identifier %s", channelIdentifier));
        }
      } else {
        if (this.packetDataSerializerClass != null) {
          // Sends 1.8 - 1.12.2 payload messages
          this.sendLegacyPayload(player, channelIdentifier, payload);
        } else {
          // Sends 1.7 payload message
          // Why should you still use this version at all?
          this.sendCustomPayloadPacket(
              player,
              this.packetPlayOutCustomPayloadConstructor.newInstance(channelIdentifier, payload));
        }
      }

    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  private void sendModernPayload(Player player, String namespace, String path, byte[] payload)
      throws InvocationTargetException, IllegalAccessException, InstantiationException {
    Object minecraftKey = this.minecraftKeyConstructor.newInstance(namespace, path);
    Object byteBuf = this.wrappedBufferMethod.invoke(null, payload);
    Object packetDataSerializer = this.packetPlayOutCustomPayloadConstructor.newInstance(byteBuf);

    this.sendCustomPayloadPacket(
        player,
        this.packetPlayOutCustomPayloadConstructor.newInstance(minecraftKey, packetDataSerializer));
  }

  private void sendLegacyPayload(Player player, String channelIdentifier, byte[] payload)
      throws IllegalAccessException, InvocationTargetException, InstantiationException {
    Object byteBuf = this.wrappedBufferMethod.invoke(null, payload);
    Object packetDataSerializer = this.packetDataSerializerConstructor.newInstance(byteBuf);

    this.sendCustomPayloadPacket(
        player,
        this.packetPlayOutCustomPayloadConstructor.newInstance(
            channelIdentifier, packetDataSerializer));
  }

  private void sendCustomPayloadPacket(Player player, Object packet)
      throws InvocationTargetException, IllegalAccessException {
    Object entityPlayer = this.getHandleMethod.invoke(player);
    Object playerConnection = this.playerConnectionField.get(entityPlayer);

    this.sendPacketMethod.invoke(playerConnection, packet);
  }

  private Class<?> getNMSClass(String className) {
    return this.getClass("net.minecraft.server." + this.version + "." + className);
  }

  private Class<?> getOBCClass(String className) {
    return this.getClass("org.bukkit.craftbukkit." + this.version + "." + className);
  }

  private Class<?> getClass(String name) {
    try {
      return Class.forName(name);
    } catch (ClassNotFoundException exception) {
      throw new RuntimeException(String.format("Failed to find the %s class", name));
    }
  }

  private Constructor<?> getConstructor(Class<?> owner, Class<?>... parameters) {
    return this.getConstructor(owner, false, parameters);
  }

  private Constructor<?> getConstructor(Class<?> owner, boolean canThrown, Class<?>... parameters) {
    for (Constructor<?> constructor : owner.getDeclaredConstructors()) {
      if (Arrays.equals(constructor.getParameterTypes(), parameters)) {
        constructor.setAccessible(true);
        return constructor;
      }
    }

    if (canThrown) {
      throw new RuntimeException(
          String.format(
              "Failed to find the %s constructors with the given parameters: %s",
              owner.getName(), this.getParametersAsString(parameters)));
    }

    return null;
  }

  private Method getMethod(Class<?> owner, String methodName, Class<?>... parameters) {
    for (Method method : owner.getDeclaredMethods()) {
      if (method.getName().equals(methodName)) {
        if (parameters.length > 0) {
          if (Arrays.equals(method.getParameterTypes(), parameters)) {
            method.setAccessible(true);
            return method;
          }
        } else {
          method.setAccessible(true);
          return method;
        }
      }
    }

    throw new RuntimeException(
        String.format(
            "Failed to find %s#%s(%s)",
            owner.getName(), methodName, this.getParametersAsString(parameters)));
  }

  private Field getField(Class<?> owner, String fieldName) {
    for (Field field : owner.getDeclaredFields()) {
      if (field.getName().equals(fieldName)) {
        field.setAccessible(true);
        return field;
      }
    }

    throw new RuntimeException(
        String.format(
            "Failed to find the %s field of the owner class %s", fieldName, owner.getName()));
  }

  private String getParametersAsString(Class<?>... parameters) {
    StringBuilder builder = new StringBuilder();

    for (int i = 0; i < parameters.length; i++) {
      Class<?> parameter = parameters[i];

      if (i == parameters.length - 1) {
        builder.append(parameter.getName());
        break;
      }

      builder.append(parameter.getName()).append(", ");
    }

    return builder.toString();
  }
}
