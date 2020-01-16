package io.vacco.summitdb.options;

import io.vacco.summitdb.commands.SdBase;

public class SdKeys implements SdOptions {

  private String pattern;
  private SdPivot pivot;
  private SdLimit limit;
  private SdSort order;
  private boolean withValues;

  public SdKeys pattern(String pattern) {
    this.pattern = pattern;
    return this;
  }

  public SdKeys pivot(SdPivot pivot) {
    this.pivot = pivot;
    return this;
  }

  public SdKeys limit(SdLimit limit) {
    this.limit = limit;
    return this;
  }

  public SdKeys order(SdSort order) {
    this.order = order;
    return this;
  }

  public SdKeys withValues(boolean withValues) {
    this.withValues = withValues;
    return this;
  }

  @Override public Object[] toArgs() {
    return SdBase.flatten(new Object[] {
        "KEYS", pattern,
        pivot != null ? pivot.toArgs() : null,
        limit != null ? limit.toArgs() : null,
        order != null ? order.toArgs() : null,
        withValues ? "WITHVALUES" : null
    }).toArray();
  }
}
