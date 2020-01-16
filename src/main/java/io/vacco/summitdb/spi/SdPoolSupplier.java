package io.vacco.summitdb.spi;

import io.vacco.redis.*;
import io.vacco.summitdb.SdNode;
import stormpot.*;

public interface SdPoolSupplier extends FailableSupplier<PoolBuilder<Pooled<Redis>>> {
  void onLeaderChange(SdNode leader);
}
