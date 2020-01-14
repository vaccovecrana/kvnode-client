package io.vacco.summitdb.options;

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
