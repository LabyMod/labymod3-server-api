package net.labymod.serverapi.bukkit.payload.transmitter;

import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.MinecraftKey;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.lang.reflect.InvocationTargetException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class BukkitPayloadTransmitter {

  private static final BukkitPayloadTransmitter instance = new BukkitPayloadTransmitter();
  private static final int AQUATIC_UPDATE = 393;

  private final boolean legacy;

  private BukkitPayloadTransmitter() {
    this.legacy = this.isLegacyVersion();
  }

  public static void transmitPayload(Player player, String channelIdentifier, byte[] payload) {
    instance.sendPayload(player, channelIdentifier, payload);
  }

  public void sendPayload(Player player, String channelIdentifier, byte[] payload) {
    try {

      if (this.legacy) {
        this.sendLegacyPayload(player, channelIdentifier, payload);
      } else {
        if (channelIdentifier.contains(":")) {
          channelIdentifier = channelIdentifier.toLowerCase();
          String[] identifiers = channelIdentifier.split(":");

          if (identifiers.length == 2) {
            // Sends 1.13 + payload messages
            this.sendModernPayload(player, identifiers[0], identifiers[1], payload);

          } else {
            throw new RuntimeException(
                String.format("Invalid payload channel identifier %s", channelIdentifier));
          }
        } else {
          throw new RuntimeException(
              String.format("Invalid payload channel identifier %s", channelIdentifier));
        }
      }

    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  private void sendModernPayload(Player player, String namespace, String path, byte[] payload) {
    PacketContainer packet = new PacketContainer(Server.CUSTOM_PAYLOAD);
    packet.getMinecraftKeys().write(0, new MinecraftKey(namespace, path));
    setPayload(packet, Unpooled.wrappedBuffer(payload));

    this.sendCustomPayloadPacket(player, packet);
  }

  private void sendLegacyPayload(Player player, String channelIdentifier, byte[] payload) {

    PacketContainer packet = new PacketContainer(Server.CUSTOM_PAYLOAD);

    packet.getStrings().write(0, channelIdentifier);
    setPayload(packet, Unpooled.wrappedBuffer(payload));

    this.sendCustomPayloadPacket(player, packet);
  }

  private void setPayload(PacketContainer packetContainer, ByteBuf byteBuf) {
    if (MinecraftReflection.is(MinecraftReflection.getPacketDataSerializerClass(), byteBuf)) {
      packetContainer.getModifier().withType(ByteBuf.class).write(0, byteBuf);
    } else {
      Object serializer = MinecraftReflection.getPacketDataSerializer(byteBuf);
      packetContainer.getModifier().withType(ByteBuf.class).write(0, serializer);
    }
  }

  private void sendCustomPayloadPacket(Player player, PacketContainer packet) {
    try {
      ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
    } catch (InvocationTargetException exception) {
      exception.printStackTrace();
    }
  }

  private boolean isLegacyVersion() {
    String version = Bukkit.getServer().getBukkitVersion().replace("_", ".");

    return version.contains("1.7")
        || version.contains("1.8")
        || version.contains("1.9")
        || version.contains("1.10")
        || version.contains("1.11")
        || version.contains("1.12");
  }
}
