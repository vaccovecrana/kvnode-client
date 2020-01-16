package io.vacco.summitdb;

import io.vacco.redis.*;
import io.vacco.summitdb.spi.SdPoolSupplier;
import stormpot.*;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class SdClient {

  private final SdPoolSupplier poolSupplier;
  private Pool<Pooled<Redis>> pool;
  private Timeout timeout = new Timeout(5, TimeUnit.SECONDS);

  public SdClient(SdPoolSupplier supplier) {
    this.poolSupplier = Objects.requireNonNull(supplier);
    this.pool = Failable.doTryRt(() -> this.poolSupplier.get().build());
  }

  public SdClient(SdAllocator a) {
    this(new SdPoolSupplier() {
      @Override public void onLeaderChange(SdNode leader) { a.getConfig().node = leader; }
      @Override public PoolBuilder<Pooled<Redis>> get() { return Pool.from(a); }
    });
  }

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
      if (e instanceof ServerError && e.getMessage() != null && e.getMessage().startsWith("TRY")) {
        String[] leader = e.getMessage().split(" ")[1].split(":");
        poolSupplier.onLeaderChange(SdNode.from(leader[0], Integer.parseInt(leader[1])));
        pool.shutdown();
        this.pool = Failable.doTryRt(() -> this.poolSupplier.get().build());
        return withConn(fn);
      } else { throw new IllegalStateException(e); }
    } finally {
      if (pr != null) { pr.release(); }
    }
  }

  public void withConn(FailableConsumer<Redis> c) {
    withConn(r -> {
      c.accept(r);
      return null;
    });
  }
}
