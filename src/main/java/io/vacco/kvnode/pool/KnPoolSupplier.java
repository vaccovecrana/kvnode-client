package io.vacco.kvnode.pool;

import io.vacco.kvnode.fn.KfnSupplier;
import io.vacco.kvnode.KnNode;
import io.vacco.kvnode.protocol.KnRedis;
import stormpot.*;

public interface KnPoolSupplier extends KfnSupplier<PoolBuilder<Pooled<KnRedis>>> {
  void onLeaderChange(KnNode leader);
}
