package io.vacco.summitdb;

import io.vacco.redis.*;
import stormpot.*;

public interface SdPoolSupplier extends FailableSupplier<PoolBuilder<Pooled<Redis>>> {
  void onLeaderChange(SdNode leader);
}
