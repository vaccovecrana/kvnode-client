package io.vacco.summitdb;

import io.vacco.redis.Failable;
import io.vacco.redis.Redis;
import stormpot.*;
import java.net.Socket;
import java.util.Objects;

public class SdAllocator implements Allocator<Pooled<Redis>> {

  private final SdClientConfig config;

  public SdAllocator(SdClientConfig config) {
    this.config = Objects.requireNonNull(config);
  }

  @Override
  public Pooled<Redis> allocate(Slot slot) {
    return new Pooled<>(slot, Failable.doTryRt(() -> {
      if (config.inputBufferSize != -1 && config.outputBufferSize != -1) {
        return new Redis(new Socket(config.node.host, config.node.port), config.inputBufferSize, config.outputBufferSize);
      }
      return new Redis(new Socket(config.node.host, config.node.port));
    }));
  }

  @Override
  public void deallocate(Pooled<Redis> poolable) {
    poolable.object.close();
  }

  public SdClientConfig getConfig() { return config; }
}
