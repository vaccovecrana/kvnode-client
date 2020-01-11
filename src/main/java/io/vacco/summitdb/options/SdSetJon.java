package io.vacco.summitdb.options;

import static io.vacco.summitdb.commands.SdBase.stringArgs;

public class SdSetJon implements SdOptions {

  private String key, path, value;
  private boolean raw;

  public SdSetJon key(String key) {
    this.key = key;
    return this;
  }

  public SdSetJon path(String path) {
    this.path = path;
    return this;
  }

  public SdSetJon value(String value) {
    this.value = value;
    return this;
  }

  public SdSetJon raw(boolean raw) {
    this.raw = raw;
    return this;
  }

  public Object[] toArgs() {
    return stringArgs(new Object[] {
        "SET", key, path, value, raw ? "RAW" : null
    });
  }
}
