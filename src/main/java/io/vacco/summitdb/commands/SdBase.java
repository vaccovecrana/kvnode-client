package io.vacco.summitdb.commands;

import io.vacco.redis.Redis;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class SdBase {

  public static String rawStrCmd(Redis r, Object ... args) throws IOException {
    byte[] response = r.call(args);
    if (response != null) {
      return new String(response);
    }
    return null;
  }

  public static Object[] stringArgs(Object[] in) {
    return Arrays.stream(in)
        .filter(Objects::nonNull)
        .flatMap(o -> o instanceof Object[] ?
            Arrays.stream((Object[]) o) :
            Arrays.stream(new Object[] {o})
        ).map(Object::toString).toArray();
  }

  public static String flushDb(Redis r) throws IOException {
    return rawStrCmd(r, "FLUSHDB");
  }
}
