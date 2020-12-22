package net.labymod.serverapi.bukkit.payload.transmitter;

import com.comphenix.protocol.PacketType.Play.Client;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.utility.MinecraftProtocolVersion;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.utility.MinecraftVersion;
import com.comphenix.protocol.wrappers.MinecraftKey;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.lang.reflect.InvocationTargetException;
import org.bukkit.entity.Player;

public final class BukkitPayloadTransmitter {

  private static final BukkitPayloadTransmitter instance = new BukkitPayloadTransmitter();

  private final ProtocolManager protocolManager;

  private BukkitPayloadTransmitter() {
    this.protocolManager = ProtocolLibrary.getProtocolManager();
  }

  public static void transmitPayload(Player player, String channelIdentifier, byte[] payload) {
    instance.sendPayload(player, channelIdentifier, payload);
  }

  public void sendPayload(Player player, String channelIdentifier, byte[] payload) {
    try {

      boolean legacy =
          this.protocolManager.getProtocolVersion(player)
              < MinecraftProtocolVersion.getVersion(MinecraftVersion.AQUATIC_UPDATE);

      if (channelIdentifier.contains(":")) {
        channelIdentifier = channelIdentifier.toLowerCase();
        String[] identifiers = channelIdentifier.split(":");

        if (identifiers.length == 2) {
          if (!legacy) {
            // Sends 1.13 + payload messages
            this.sendModernPayload(player, identifiers[0], identifiers[1], payload);
          }
        } else {
          throw new RuntimeException(
              String.format("Invalid payload channel identifier %s", channelIdentifier));
        }
      } else {
        if (legacy) {
          // Sends 1.8 - 1.12.2 payload messages
          this.sendLegacyPayload(player, channelIdentifier, payload);
        }
      }

    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  private void sendModernPayload(Player player, String namespace, String path, byte[] payload) {
    PacketContainer packet = new PacketContainer(Client.CUSTOM_PAYLOAD);
    packet.getMinecraftKeys().write(0, new MinecraftKey(namespace, path));
    setPayload(packet, Unpooled.wrappedBuffer(payload));

    this.sendCustomPayloadPacket(player, packet);
  }

  private void sendLegacyPayload(Player player, String channelIdentifier, byte[] payload) {

    PacketContainer packet = new PacketContainer(Client.CUSTOM_PAYLOAD);

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
      this.protocolManager.sendServerPacket(player, packet);
    } catch (InvocationTargetException exception) {
      exception.printStackTrace();
    }
  }
}
