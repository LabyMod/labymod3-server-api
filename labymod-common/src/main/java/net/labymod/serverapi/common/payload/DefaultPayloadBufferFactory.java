package net.labymod.serverapi.common.payload;

import io.netty.buffer.ByteBuf;
import net.labymod.serverapi.api.payload.PayloadBuffer;

public class DefaultPayloadBufferFactory implements PayloadBuffer.Factory {

  @Override
  public PayloadBuffer create() {
    return new DefaultPayloadBuffer();
  }

  @Override
  public PayloadBuffer create(ByteBuf byteBuf) {
    return new DefaultPayloadBuffer(byteBuf);
  }
}
