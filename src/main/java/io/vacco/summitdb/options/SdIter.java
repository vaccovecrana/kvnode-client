package io.vacco.summitdb.options;

import io.vacco.summitdb.commands.SdBase;

public class SdIter implements SdOptions {

  private String index, pivot, match, rangeMin, rangeMax;
  private boolean asc, desc;
  private SdLimit limit;

  public SdIter index(String name) {
    this.index = name;
    return this;
  }

  public SdIter pivot(String value) {
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

  public SdIter desc(boolean desc) {
    this.desc = desc;
    return this;
  }

  public SdIter asc(boolean asc) {
    this.asc = asc;
    return this;
  }

  @Override
  public Object[] toArgs() {
    return SdBase.flatten(new Object[] {
        "ITER", index,
        pivot != null ? new Object[] {"PIVOT", pivot} : null,
        match != null ? new Object[] {"MATCH", match} : null,
        (rangeMin != null || rangeMax != null) ? new Object[] {"RANGE", rangeMin, rangeMax} : null,
        limit != null ? limit.toArgs() : null,
        desc ? "DESC" : null, asc ? "ASC" : null
    }).toArray();
  }
}
