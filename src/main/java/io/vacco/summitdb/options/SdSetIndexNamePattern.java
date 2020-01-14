package io.vacco.summitdb.options;

public class SdSetIndexNamePattern implements SdOptions {

  private String name, pattern;

  public SdSetIndexNamePattern name(String name) {
    this.name = name;
    return this;
  }

  public SdSetIndexNamePattern pattern(String pattern) {
    this.pattern = pattern;
    return this;
  }

  @Override public Object[] toArgs() {
    return new Object[] { name, pattern };
  }
}
