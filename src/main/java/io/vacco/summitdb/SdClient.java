package io.vacco.summitdb;

import io.vacco.redis.*;
import stormpot.*;
import java.util.concurrent.TimeUnit;

public class SdClient {

  private final Pool<Pooled<Redis>> pool;
  private Timeout timeout = new Timeout(5, TimeUnit.SECONDS);

  public SdClient(FailableSupplier<PoolBuilder<Pooled<Redis>>> poolSupplier) {
    this.pool = Failable.doTryRt(poolSupplier).build();
  }
  public SdClient(SdAllocator a) { this(() -> Pool.from(a)); }
  public SdClient(SdClientConfig cfg) { this(new SdAllocator(cfg)); }
  public SdClient(String host, int port) { this(SdClientConfig.of(host, port)); }

  public Pool<Pooled<Redis>> getPool() { return pool; }
  public void setTimeout(long duration, TimeUnit unit) {
    this.timeout = new Timeout(duration, unit);
  }

  public <O> O withConn(FailableFn<Redis, O> fn) {
    Pooled<Redis> pr = null;
    try {
      pr = pool.claim(timeout);
      return fn.apply(pr.object);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    } finally {
      if (pr != null) {
        pr.release();
      }
    }
  }

  public void withConn(FailableConsumer<Redis> c) {
    withConn(r -> {
      c.accept(r);
      return null;
    });
  }
}
