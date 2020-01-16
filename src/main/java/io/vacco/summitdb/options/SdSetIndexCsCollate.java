package io.vacco.summitdb.options;

import io.vacco.summitdb.spi.SdOptions;

public class SdSetIndexCsCollate implements SdOptions {

  private boolean cs;
  private String collate;

  public SdSetIndexCsCollate cs(boolean cs) {
    this.cs = cs;
    return this;
  }

  public SdSetIndexCsCollate collate(String collate) {
    this.collate = collate;
    return this;
  }

  @Override
  public Object[] toArgs() {
    return new Object[] {
        cs ? "CS" : null,
        collate != null ? new Object[] {"COLLATE", collate} : null
    };
  }
}
