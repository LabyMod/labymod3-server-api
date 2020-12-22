package net.labymod.serverapi.common.protocol.chunkcaching;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import java.util.Objects;
import net.labymod.serverapi.api.protocol.chunkcaching.ChunkPosition;

public class DefaultChunkPosition implements ChunkPosition {

  private final int x;
  private final int z;

  @AssistedInject
  private DefaultChunkPosition(@Assisted("x") int x, @Assisted("z") int z) {
    this.x = x;
    this.z = z;
  }

  /** {@inheritDoc} */
  @Override
  public int getX() {
    return this.x;
  }

  /** {@inheritDoc} */
  @Override
  public int getZ() {
    return this.z;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DefaultChunkPosition that = (DefaultChunkPosition) o;
    return x == that.x && z == that.z;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, z);
  }
}
