package io.vacco.summitdb.commands;

import io.vacco.redis.Redis;
import io.vacco.summitdb.options.SdMSet;
import io.vacco.summitdb.options.SdSetEx;

import java.io.IOException;

public class SdKeyValue {

  public static long append(Redis r, String key, String value) throws IOException {
    return r.call("APPEND", key, value);
  }

  public static long exists(Redis r, String ... keys) throws IOException {
    Object[] oa = new Object[keys.length + 1];
    oa[0] = "EXISTS";
    for (int i = 0; i < keys.length; i++) {
      oa[i + 1] = keys[i];
    }
    return r.call(oa);
  }

  public static String get(Redis r, String key) throws IOException {
    return SdBase.rawStrCmd(r, "GET", key);
  }

  public static String mset(Redis r, SdMSet mSet) throws IOException {
    return SdBase.rawStrCmd(r, mSet.toArgs());
  }

  public static String set(Redis r, String key, String value) throws IOException {
    return SdBase.rawStrCmd(r, "SET", key, value);
  }

  public static String set(Redis r, SdSetEx set) throws IOException {
    return SdBase.rawStrCmd(r, set.toArgs());
  }

}
