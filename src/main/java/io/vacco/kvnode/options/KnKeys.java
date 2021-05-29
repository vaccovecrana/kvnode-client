package io.vacco.kvnode.options;

import io.vacco.kvnode.commands.KnBase;

public class KnKeys implements KnOptions {

  private String pattern;

  public KnKeys pattern(String pattern) {
    this.pattern = pattern;
    return this;
  }

  @Override public Object[] toArgs() {
    return KnBase.flatten(new Object[] {"KEYS", pattern}).toArray();
  }
}
