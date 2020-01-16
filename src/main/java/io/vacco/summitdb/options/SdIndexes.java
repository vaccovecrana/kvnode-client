package io.vacco.summitdb.options;

import io.vacco.summitdb.commands.SdBase;
import io.vacco.summitdb.spi.SdOptions;

public class SdIndexes implements SdOptions {

  private String pattern;
  private boolean details;

  public SdIndexes pattern(String pattern) {
    this.pattern = pattern;
    return this;
  }

  public SdIndexes details(boolean details) {
    this.details = details;
    return this;
  }

  public boolean isDetails() { return details; }

  @Override public Object[] toArgs() {
    return SdBase.flatten(new Object[] {
        "INDEXES", pattern, details ? "DETAILS" : null
    }).toArray();
  }
}
