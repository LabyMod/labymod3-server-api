package net.labymod.serverapi.api.payload;

import io.netty.buffer.ByteBuf;

/** Represents a payload buffer to write something to the payload channels. */
public interface PayloadBuffer {

  /**
   * Reads a string from the byte buffer.
   *
   * @return The read string.
   */
  String readString();

  /**
   * Writes a string to the byte buffer.
   *
   * @param content The that ssould be written to the byte buffer.
   * @return This object object for chaining.
   */
  PayloadBuffer writeString(String content);

  /**
   * Reads a varint from the byte buffer.
   *
   * @return The read varint.
   */
  int readVarIntFromBuffer();

  /**
   * Writes a varint into the byte buffer.
   *
   * @param input The input that should be written to the buffer.
   * @return This object for chaining.
   */
  PayloadBuffer writeVarIntIntoBuffer(int input);

  /**
   * Retrieves all readable bytes from the byte buffer.
   *
   * @return All readable bytes from the byte buffer.
   */
  byte[] getBytes();

  /** A factory for creating {@link PayloadBuffer}. */
  interface Factory {

    /**
     * Creates a new {@link PayloadBuffer}.
     *
     * @return A created payload buffer.
     */
    PayloadBuffer create();

    /**
     * Creates a new {@link PayloadBuffer} wit the given {@code byteBuf}.
     *
     * @param byteBuf The byte buf for the payload buffer.
     * @return A created payload buffer.
     */
    PayloadBuffer create(ByteBuf byteBuf);
  }
}
