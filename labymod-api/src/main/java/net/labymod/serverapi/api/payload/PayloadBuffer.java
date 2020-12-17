package net.labymod.serverapi.api.payload;

import com.google.inject.assistedinject.Assisted;
import io.netty.buffer.ByteBuf;

public interface PayloadBuffer {

  String readString();

  PayloadBuffer writeString(String content);

  int readVarIntFromBuffer();

  PayloadBuffer writeVarIntInfoBuffer(int input);

  byte[] getBytes();

  interface Factory {

    PayloadBuffer create();

    PayloadBuffer create(@Assisted ByteBuf byteBuf);

  }

}
