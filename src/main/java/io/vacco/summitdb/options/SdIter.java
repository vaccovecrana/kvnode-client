package io.vacco.summitdb.options;

import io.vacco.summitdb.commands.SdBase;

public class SdIter implements SdOptions {

  private String index, match, rangeMin, rangeMax;
  private SdLimit limit;
  private SdPivot pivot;
  private SdSort order;

  public SdIter index(String name) {
    this.index = name;
    return this;
  }

  public SdIter pivot(SdPivot value) {
    this.pivot = value;
    return this;
  }

  public SdIter match(String pattern) {
    this.match = pattern;
    return this;
  }

  public SdIter rangeMin(String min) {
    this.rangeMin = min;
    return this;
  }

  public SdIter rangeMax(String max) {
    this.rangeMax = max;
    return this;
  }

  public SdIter limit(SdLimit limit) {
    this.limit = limit;
    return this;
  }

  public SdIter order(SdSort order) {
    this.order = order;
    return this;
  }

  @Override
  public Object[] toArgs() {
    return SdBase.flatten(new Object[] {
        "ITER", index,
        pivot != null ? pivot.toArgs() : null,
        match != null ? new Object[] {"MATCH", match} : null,
        (rangeMin != null || rangeMax != null) ? new Object[] {"RANGE", rangeMin, rangeMax} : null,
        limit != null ? limit.toArgs() : null,
        order != null ? order.toArgs() : null
    }).toArray();
  }
}
