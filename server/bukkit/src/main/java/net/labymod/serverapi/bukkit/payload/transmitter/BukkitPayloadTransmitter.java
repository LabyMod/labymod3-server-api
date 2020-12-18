package net.labymod.serverapi.bukkit.payload.transmitter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.bukkit.entity.Player;

public final class BukkitPayloadTransmitter {

  private static final BukkitPayloadTransmitter instance = new BukkitPayloadTransmitter();

  /** Retrieves the `getHandle()` method. */
  private final Method getHandleMethod;
  /** Retrieves the player connection field. */
  private final Field playerConnectionField;
  /** Retrieves the send packet method */
  private final Method sendPacketMethod;

  private Method wrappedBufferMethod;

  private Constructor<?> packetPlayOutCustomPayloadConstructor;

  // Minecraft 1.8 - 1.12.2 Support
  private Class<?> packetDataSerializerClass;
  private Constructor<?> packetDataSerializerConstructor;

  // Minecraft 1.13+ Support
  private Class<?> minecraftKeyClass;
  private Constructor<?> minecraftKeyConstructor;

  private BukkitPayloadTransmitter() {
    ReflectionHelper reflectionHelper = ReflectionHelper.getInstance();

    Class<?> craftPlayerClass = reflectionHelper.getOBCClass("entity.CraftPlayer");
    Class<?> entityPlayerClass = reflectionHelper.getNMSClass("EntityPlayer");
    Class<?> playerConnectionClass = reflectionHelper.getNMSClass("PlayerConnection");
    Class<?> packetPlayOutCustomPayloadClass =
        reflectionHelper.getNMSClass("PacketPlayOutCustomPayload");

    this.packetPlayOutCustomPayloadConstructor =
        reflectionHelper.getConstructor(
            packetPlayOutCustomPayloadClass, String.class, byte[].class);

    if (this.packetPlayOutCustomPayloadConstructor == null) {

      this.packetDataSerializerClass = reflectionHelper.getNMSClass("PacketDataSerializer");
      Class<?> byteBufClass = reflectionHelper.getClass("io.netty.buffer.ByteBuf");

      this.packetDataSerializerConstructor =
          reflectionHelper.getConstructor(this.packetDataSerializerClass, true, byteBufClass);

      Class<?> unpooledClass = reflectionHelper.getClass("io.netty.buffer.Unpooled");

      this.wrappedBufferMethod =
          reflectionHelper.getMethod(unpooledClass, "wrappedBuffer", byte[].class);

      this.packetPlayOutCustomPayloadConstructor =
          reflectionHelper.getConstructor(
              packetPlayOutCustomPayloadClass, String.class, this.packetDataSerializerClass);

      if (this.packetPlayOutCustomPayloadConstructor == null) {

        this.minecraftKeyClass = reflectionHelper.getNMSClass("MinecraftKey");
        this.minecraftKeyConstructor =
            reflectionHelper.getConstructor(minecraftKeyClass, String.class, String.class);

        this.packetPlayOutCustomPayloadConstructor =
            reflectionHelper.getConstructor(
                packetPlayOutCustomPayloadClass,
                this.minecraftKeyClass,
                this.packetDataSerializerClass);
      }
    }

    this.getHandleMethod = reflectionHelper.getMethod(craftPlayerClass, "getHandle");
    this.playerConnectionField = reflectionHelper.getField(entityPlayerClass, "playerConnection");
    this.sendPacketMethod = reflectionHelper.getMethod(playerConnectionClass, "sendPacket");
  }

  public static void transmitPayload(Player player, String channelIdentifier, byte[] payload) {
    instance.sendPayload(player, channelIdentifier, payload);
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
}
