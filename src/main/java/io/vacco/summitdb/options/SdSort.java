package io.vacco.summitdb.options;

public class SdSort implements SdOptions {

  public enum Order { ASC, DESC }

  private Order order;

  public SdSort order(Order o) {
    this.order = o;
    return this;
  }

  @Override public Object[] toArgs() {
    return new Object[] { order };
  }
}
