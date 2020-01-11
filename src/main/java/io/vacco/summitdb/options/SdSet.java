package io.vacco.summitdb.options;

import static io.vacco.summitdb.commands.SdBase.*;

public class SdSet {

  private String key, value;
  private long exSeconds = -1, exMillis = -1;
  private boolean nx = false, xx = false;

  public SdSet withKey(String key) {
    this.key = key;
    return this;
  }

  public SdSet withValue(String value) {
    this.value = value;
    return this;
  }

  public SdSet withExSeconds(long seconds) {
    this.exSeconds = seconds;
    return this;
  }

  public SdSet withExMillis(long millis) {
    this.exMillis = millis;
    return this;
  }

  public SdSet withNx(boolean nx) {
    this.nx = nx;
    return this;
  }

  public SdSet withXx(boolean xx) {
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
