package io.vacco.summitdb.commands;

import io.vacco.redis.Redis;
import io.vacco.summitdb.options.SdJSet;
import java.io.IOException;

public class SdJson {

  public static String jset(Redis r, String key, String path, String value) throws IOException {
    return SdBase.rawStrCmd(r, "JSET", key, path, value);
  }

  public static String jset(Redis r, SdJSet s) throws IOException {
    return SdBase.rawStrCmd(r, s.toArgs());
  }

  public static String jget(Redis r, String key, String path) throws IOException {
    return SdBase.rawStrCmd(r, "JGET", key, path);
  }

  public static Long jdel(Redis r, String key, String path) throws IOException {
    return r.call("JDEL", key, path);
  }

}
