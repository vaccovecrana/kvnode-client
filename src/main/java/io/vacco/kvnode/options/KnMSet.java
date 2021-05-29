package io.vacco.kvnode.options;

import java.util.HashMap;
import static io.vacco.kvnode.commands.KnBase.flatten;

public class KnMSet extends HashMap<String, String> implements KnOptions {

  public static final long serialVersionUID = 1;

  public KnMSet add(String key, String value) {
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
