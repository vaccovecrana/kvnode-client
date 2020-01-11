package io.vacco.summitdb.options;

import java.util.Arrays;
import java.util.HashMap;

import static io.vacco.summitdb.commands.SdBase.stringArgs;

public class SdMSet extends HashMap<String, String> implements SdOptions {

  public SdMSet add(String key, String value) {
    put(key, value);
    return this;
  }

  public Object[] toArgs() {
    return stringArgs(new Object[] {
        "MSET",
        this.entrySet().stream()
            .flatMap(e -> Arrays.stream(new Object[] {e.getKey(), e.getValue()}))
            .toArray()
    });
  }
}
