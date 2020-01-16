package io.vacco.summitdb.options;

import io.vacco.summitdb.commands.SdBase;
import io.vacco.summitdb.spi.SdOptions;

public class SdSetIndexSpatial implements SdOptions {

  private SdSetIndexNamePattern namePattern;
  private String json;

  public SdSetIndexSpatial namePattern(SdSetIndexNamePattern namePattern) {
    this.namePattern = namePattern;
    return this;
  }

  public SdSetIndexSpatial json(String path) {
    this.json = path;
    return this;
  }

  @Override public Object[] toArgs() {
    return SdBase.flatten(new Object[] {
        "SETINDEX", namePattern.toArgs(), "SPATIAL",
        json != null ? new Object[] {"JSON", json} : null
    }).toArray();
  }
}
