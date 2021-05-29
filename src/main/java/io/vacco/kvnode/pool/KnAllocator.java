package io.vacco.kvnode.pool;

import io.vacco.kvnode.fn.KfnSupplier;
import io.vacco.kvnode.KnClientConfig;
import io.vacco.kvnode.protocol.KnRedis;
import stormpot.*;
import java.net.Socket;
import java.util.Objects;

public class KnAllocator implements Allocator<Pooled<KnRedis>> {

  private final KnClientConfig config;

  public KnAllocator(KnClientConfig config) {
    this.config = Objects.requireNonNull(config);
  }

  @Override
  public Pooled<KnRedis> allocate(Slot slot) {
    return new Pooled<>(slot, KfnSupplier.tryGet(() -> {
      Socket s = new Socket(config.node.host, config.node.port);
      if (config.inputBufferSize != -1 && config.outputBufferSize != -1) {
        return new KnRedis(s, config.inputBufferSize, config.outputBufferSize);
      }
      return new KnRedis(s);
    }));
  }

  @Override
  public void deallocate(Pooled<KnRedis> poolable) {
    poolable.object.close();
  }

  public KnClientConfig getConfig() { return config; }
}
