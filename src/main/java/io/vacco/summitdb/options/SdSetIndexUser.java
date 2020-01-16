package io.vacco.summitdb.options;

import io.vacco.summitdb.commands.SdBase;
import io.vacco.summitdb.spi.SdOptions;

public class SdSetIndexUser implements SdOptions {

  private SdSetIndexDesc desc;
  private SdSetIndexNamePattern namePattern;
  private String function;

  public SdSetIndexUser namePattern(SdSetIndexNamePattern namePattern) {
    this.namePattern = namePattern;
    return this;
  }

  public SdSetIndexUser eval(String function) {
    this.function = function;
    return this;
  }

  public SdSetIndexUser desc(SdSetIndexDesc desc) {
    this.desc = desc;
    return this;
  }

  @Override public Object[] toArgs() {
    return SdBase.flatten(new Object[] {
        "SETINDEX", namePattern.toArgs(),
        "EVAL", function, desc != null ? desc.toArgs() : null
    }).toArray();
  }
}
