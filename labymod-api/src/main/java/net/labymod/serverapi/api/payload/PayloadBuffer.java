package net.labymod.serverapi.api.payload;

public interface PayloadBuffer {

  String readString();

  PayloadBuffer writeString(String content);

  int readVarIntFromBuffer();

  PayloadBuffer writeVarIntInfoBuffer(int input);

}
