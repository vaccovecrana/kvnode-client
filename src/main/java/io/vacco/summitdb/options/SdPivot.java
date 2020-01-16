package io.vacco.summitdb.options;

public class SdPivot implements SdOptions {

  private String value;

  public SdPivot pivot(String value) {
    this.value = value;
    return this;
  }

  @Override public Object[] toArgs() {
    return new Object[] {"PIVOT", value};
  }
}
