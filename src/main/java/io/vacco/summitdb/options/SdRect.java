package io.vacco.summitdb.options;

import io.vacco.summitdb.commands.SdBase;

public class SdRect implements SdOptions {

  private String index, rectangle, pattern;
  private SdLimit limit;
  private long skip = -1;

  public SdRect index(String index) {
    this.index = index;
    return this;
  }

  public SdRect rectangle(String rectangle) {
    this.rectangle = rectangle;
    return this;
  }

  public SdRect match(String pattern) {
    this.pattern = pattern;
    return this;
  }

  public SdRect limit(SdLimit limit) {
    this.limit = limit;
    return this;
  }

  public SdRect skip(long skip) {
    this.skip = skip;
    return this;
  }

  @Override public Object[] toArgs() {
    return SdBase.flatten(new Object[] {
        "RECT", index, rectangle,
        pattern != null ? new Object[] {"MATCH", pattern} : null,
        limit != null ? limit.toArgs() : null,
        skip != -1 ? new Object[] {"SKIP", skip} : null
    }).toArray();
  }
}
