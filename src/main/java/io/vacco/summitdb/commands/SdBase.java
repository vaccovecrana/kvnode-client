package io.vacco.summitdb.commands;

import io.vacco.redis.Redis;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class SdBase {

  public static String rawStrCmd(Redis r, Object ... args) throws IOException {
    byte[] response = r.call(args);
    if (response != null) {
      return new String(response);
    }
    return null;
  }

  public static Stream<Object> flatten(Object[] array) {
    return Arrays.stream(array).filter(Objects::nonNull)
        .flatMap(o -> o instanceof Object[] ?
            flatten((Object[]) o) : Stream.of(o.toString())
        );
  }

  public static String flushDb(Redis r) throws IOException {
    return rawStrCmd(r, "FLUSHDB");
  }
}
