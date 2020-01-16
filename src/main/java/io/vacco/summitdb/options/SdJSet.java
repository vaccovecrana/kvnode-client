package io.vacco.summitdb.options;

import io.vacco.summitdb.spi.SdOptions;

import static io.vacco.summitdb.commands.SdBase.flatten;

public class SdJSet implements SdOptions {

  private String key, path, value;
  private boolean raw;

  public SdJSet key(String key) {
    this.key = key;
    return this;
  }

  public SdJSet path(String path) {
    this.path = path;
    return this;
  }

  public SdJSet value(String value) {
    this.value = value;
    return this;
  }

  public SdJSet raw(boolean raw) {
    this.raw = raw;
    return this;
  }

  public Object[] toArgs() {
    return flatten(new Object[] {
        "JSET", key, path, value, raw ? "RAW" : null
    }).toArray();
  }
}
