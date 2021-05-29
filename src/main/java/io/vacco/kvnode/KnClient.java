package io.vacco.kvnode;

import io.vacco.kvnode.fn.*;
import io.vacco.kvnode.pool.*;
import io.vacco.kvnode.protocol.*;
import stormpot.*;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class KnClient {

  private final KnPoolSupplier poolSupplier;
  private Pool<Pooled<KnRedis>> pool;
  private Timeout timeout = new Timeout(5, TimeUnit.SECONDS);

  public KnClient(KnPoolSupplier supplier) {
    this.poolSupplier = Objects.requireNonNull(supplier);
    this.pool = KfnSupplier.tryGet(() -> this.poolSupplier.get().build());
  }

  public KnClient(KnAllocator a) {
    this(new KnPoolSupplier() {
      @Override public void onLeaderChange(KnNode leader) { a.getConfig().node = leader; }
      @Override public PoolBuilder<Pooled<KnRedis>> get() { return Pool.from(a); }
    });
  }

  public KnClient(KnClientConfig cfg) { this(new KnAllocator(cfg)); }
  public KnClient(String host, int port) { this(KnClientConfig.of(host, port)); }

  public Pool<Pooled<KnRedis>> getPool() { return pool; }
  public void setTimeout(long duration, TimeUnit unit) {
    this.timeout = new Timeout(duration, unit);
  }

  public <O> O mapCon(Kfn<KnRedis, O> fn) {
    Pooled<KnRedis> pr = null;
    try {
      pr = pool.claim(timeout);
      return fn.apply(pr.object);
    } catch (Exception e) {
      if (e instanceof KnServerError && e.getMessage() != null && e.getMessage().startsWith("TRY")) {
        String[] leader = e.getMessage().split(" ")[1].split(":");
        poolSupplier.onLeaderChange(KnNode.from(leader[0], Integer.parseInt(leader[1])));
        pool.shutdown();
        this.pool = KfnSupplier.tryGet(() -> this.poolSupplier.get().build());
        return mapCon(fn);
      } else { throw new IllegalStateException(e); }
    } finally {
      if (pr != null) { pr.release(); }
    }
  }

  public void withConn(KfnConsumer<KnRedis> c) {
    mapCon(r -> {
      c.accept(r);
      return null;
    });
  }
}
