package io.vacco.summitdb.options;

import io.vacco.summitdb.commands.SdBase;

public class SdSetIndexNumber implements SdOptions {

  public enum Type { INT, UINT, FLOAT }

  private SdSetIndexNamePattern namePattern;
  private SdSetIndexDesc desc;
  private Type type;

  public SdSetIndexNumber namePattern(SdSetIndexNamePattern namePattern) {
    this.namePattern = namePattern;
    return this;
  }

  public SdSetIndexNumber type(Type t) {
    this.type = t;
    return this;
  }

  public SdSetIndexNumber desc(SdSetIndexDesc desc) {
    this.desc = desc;
    return this;
  }

  @Override
  public Object[] toArgs() {
    return SdBase.flatten(new Object[] {
        "SETINDEX", namePattern.toArgs(), type,
        desc != null ? desc.toArgs() : null
    }).toArray();
  }
}
