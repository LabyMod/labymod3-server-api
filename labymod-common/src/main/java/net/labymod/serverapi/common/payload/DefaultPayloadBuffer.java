package net.labymod.serverapi.common.payload;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.charset.StandardCharsets;
import net.labymod.serverapi.api.payload.PayloadBuffer;

public class DefaultPayloadBuffer implements PayloadBuffer {

  private final ByteBuf byteBuf;

  @AssistedInject
  private DefaultPayloadBuffer() {
    this(Unpooled.buffer());
  }

  @AssistedInject
  private DefaultPayloadBuffer(@Assisted ByteBuf byteBuf) {
    this.byteBuf = byteBuf;
  }

  /** {@inheritDoc} */
  @Override
  public String readString() {
    int varIntFromBuffer = this.readVarIntFromBuffer();
    byte[] buffer = new byte[varIntFromBuffer];

    this.byteBuf.readBytes(buffer, 0, varIntFromBuffer);

    return new String(buffer, StandardCharsets.UTF_8);
  }

  /** {@inheritDoc} */
  @Override
  public PayloadBuffer writeString(String content) {
    byte[] data = content.getBytes(StandardCharsets.UTF_8);

    this.writeVarIntInfoBuffer(data.length);
    this.byteBuf.writeBytes(data);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public int readVarIntFromBuffer() {
    int readingNumber = 0;
    int result = 0;

    byte readableBytes;

    do {
      readableBytes = byteBuf.readByte();
      readingNumber |= (readableBytes & 127) << result++ * 7;
    } while ((readableBytes & 128) == 128);

    return readingNumber;
  }

  /** {@inheritDoc} */
  @Override
  public PayloadBuffer writeVarIntInfoBuffer(int input) {
    while ((input & 128) != 0) {
      this.byteBuf.writeByte(input & 127 | 128);
      input >>>= 7;
    }

    this.byteBuf.writeByte(input);

    return this;
  }

  /** {@inheritDoc} */
  @Override
  public byte[] getBytes() {
    byte[] bytes = new byte[this.byteBuf.readableBytes()];
    this.byteBuf.readBytes(bytes);
    return bytes;
  }
}
