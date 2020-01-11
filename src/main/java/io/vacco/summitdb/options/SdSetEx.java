package io.vacco.summitdb.options;

import static io.vacco.summitdb.commands.SdBase.*;

public class SdSetEx implements SdOptions {

  private String key, value;
  private long exSeconds = -1, exMillis = -1;
  private boolean nx = false, xx = false;

  public SdSetEx key(String key) {
    this.key = key;
    return this;
  }

  public SdSetEx value(String value) {
    this.value = value;
    return this;
  }

  public SdSetEx exSeconds(long seconds) {
    this.exSeconds = seconds;
    return this;
  }

  public SdSetEx exMillis(long millis) {
    this.exMillis = millis;
    return this;
  }

  public SdSetEx nx(boolean nx) {
    this.nx = nx;
    return this;
  }

  public SdSetEx xx(boolean xx) {
    this.xx = xx;
    return this;
  }

  public Object[] toArgs() {
    return stringArgs(new Object[] {
        "SET", key, value,
        exSeconds != -1 ? new Object[] {"EX", exSeconds} : null,
        exMillis != -1 ? new Object[] {"PX", exMillis} : null,
        nx ? "NX" : null, xx ? "XX" : null
    });
  }
}
