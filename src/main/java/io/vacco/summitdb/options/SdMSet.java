package io.vacco.summitdb.options;

import java.util.HashMap;
import static io.vacco.summitdb.commands.SdBase.flatten;

public class SdMSet extends HashMap<String, String> implements SdOptions {

  public SdMSet add(String key, String value) {
    put(key, value);
    return this;
  }

  public Object[] toArgs() {
    return flatten(new Object[] {
        "MSET",
        this.entrySet().stream()
            .map(e -> new Object[] {e.getKey(), e.getValue()})
            .toArray()
    }).toArray();
  }
}
