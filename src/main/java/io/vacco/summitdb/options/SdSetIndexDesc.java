package io.vacco.summitdb.options;

import io.vacco.summitdb.spi.SdOptions;

public class SdSetIndexDesc implements SdOptions {

  private boolean desc;

  public SdSetIndexDesc desc(boolean desc) {
    this.desc = desc;
    return this;
  }

  @Override public Object[] toArgs() {
    return new Object[]{ desc ? "DESC" : null };
  }
}
