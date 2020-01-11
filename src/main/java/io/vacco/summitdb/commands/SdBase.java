package io.vacco.summitdb.commands;

import io.vacco.redis.Redis;
import java.io.IOException;

public class SdBase {

  public static String rawStrCmd(Redis r, Object ... args) throws IOException {
    byte[] response = r.call(args);
    if (response != null) {
      return new String(response);
    }
    return null;
  }

}
