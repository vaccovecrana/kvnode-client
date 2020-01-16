package io.vacco.summitdb.commands;

import io.vacco.redis.Redis;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SdBase {

  public static String rawStrCmd(Redis r, Object ... args) throws IOException {
    byte[] response = r.call(args);
    if (response != null) {
      return new String(response);
    }
    return null;
  }

  public static List<String> toStringList(List<byte[]> raw) {
    return raw.stream().map(String::new).collect(Collectors.toList());
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
