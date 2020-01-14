package io.vacco.summitdb.options;

public class SdRect {

  private String index, rectangle, pattern;
  private SdLimit limit;
  private long skip;

  public SdRect limit(SdLimit limit) {
    this.limit = limit;
    return this;
  }

  public SdRect index(String index) {
    this.index = index;
    return this;
  }

}
