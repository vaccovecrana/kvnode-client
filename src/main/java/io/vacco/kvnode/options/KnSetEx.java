package io.vacco.kvnode.options;

import static io.vacco.kvnode.commands.KnBase.flatten;

public class KnSetEx implements KnOptions {

  private String key, value;
  private long exSeconds = -1, exMillis = -1;
  private boolean nx = false, xx = false;

  public KnSetEx key(String key) {
    this.key = key;
    return this;
  }

  public KnSetEx value(String value) {
    this.value = value;
    return this;
  }

  public KnSetEx exSeconds(long seconds) {
    this.exSeconds = seconds;
    return this;
  }

  public KnSetEx exMillis(long millis) {
    this.exMillis = millis;
    return this;
  }

  public KnSetEx nx(boolean nx) {
    this.nx = nx;
    return this;
  }

  public KnSetEx xx(boolean xx) {
    this.xx = xx;
    return this;
  }

  public Object[] toArgs() {
    return flatten(new Object[] {
        "SET", key, value,
        exSeconds != -1 ? new Object[] {"EX", exSeconds} : null,
        exMillis != -1 ? new Object[] {"PX", exMillis} : null,
        nx ? "NX" : null, xx ? "XX" : null
    }).toArray();
  }
}
