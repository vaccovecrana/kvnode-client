package io.vacco.summitdb;

import io.vacco.redis.Failable;
import io.vacco.redis.FailableSupplier;
import io.vacco.redis.Redis;
import stormpot.Pool;
import stormpot.PoolBuilder;
import stormpot.Pooled;

public class SdClient {

  private final Pool<Pooled<Redis>> pool;

  public SdClient(SdAllocator allocator, FailableSupplier<PoolBuilder<Pooled<Redis>>> poolSupplier) {
    this.pool = Failable.doTryRt(poolSupplier).build();
  }

  public SdClient(SdAllocator a) { this(a, () -> Pool.from(a)); }
  public SdClient(SdClientConfig cfg) { this(new SdAllocator(cfg)); }
  public SdClient(String host, int port) { this(SdClientConfig.of(host, port, -1, -1)); }

  public Pool<Pooled<Redis>> getPool() { return pool; }
}
