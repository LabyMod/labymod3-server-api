package net.labymod.serverapi.common.payload;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import net.labymod.serverapi.api.payload.PayloadBuffer;

import java.nio.charset.StandardCharsets;

public class DefaultPayloadBuffer implements PayloadBuffer {

  private final ByteBuf byteBuf;

  protected DefaultPayloadBuffer() {
    this(Unpooled.buffer());
  }

  protected DefaultPayloadBuffer(ByteBuf byteBuf) {
    this.byteBuf = byteBuf;
  }

  /** {@inheritDoc} */
  @Override
  public String readString() {
    int varIntFromBuffer = this.readVarIntFromBuffer();
    int maxLength = Short.MAX_VALUE;

    if (varIntFromBuffer > maxLength * 4) {
      throw new DecoderException(
          "The received encoded string buffer is longer than maximum allowed ("
              + varIntFromBuffer
              + " > "
              + maxLength * 4
              + ")");
    } else if (varIntFromBuffer < 0) {
      throw new DecoderException(
          "The received encoded string buffer length is less than zero! Weird string!");
    } else {
      byte[] buffer = new byte[varIntFromBuffer];
      this.byteBuf.readBytes(buffer);

      String content = new String(buffer, StandardCharsets.UTF_8);

      if (content.length() > maxLength) {
        throw new DecoderException(
            "The received string length is longer than maximum allowed ("
                + varIntFromBuffer
                + " > "
                + maxLength
                + ")");
      } else {
        return content;
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public PayloadBuffer writeString(String content) {
    byte[] data = content.getBytes(StandardCharsets.UTF_8);

    if (data.length > Short.MAX_VALUE) {
      throw new EncoderException(
          "String too big (was "
              + content.length()
              + " bytes encoded, max "
              + Short.MAX_VALUE
              + ")");
    } else {
      this.writeVarIntInfoBuffer(data.length);
      this.byteBuf.writeBytes(data);
    }

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
    while ((input & -128) != 0) {
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
