package net.labymod.serverapi.api.payload;


import io.netty.buffer.ByteBuf;

public interface PayloadBuffer {

  String readString();

  PayloadBuffer writeString(String content);

  int readVarIntFromBuffer();

  PayloadBuffer writeVarIntInfoBuffer(int input);

  byte[] getBytes();

  interface Factory {

    PayloadBuffer create();

    PayloadBuffer create(ByteBuf byteBuf);

  }

}
