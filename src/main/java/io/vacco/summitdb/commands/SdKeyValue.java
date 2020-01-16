package io.vacco.summitdb.commands;

import io.vacco.redis.Redis;
import io.vacco.summitdb.options.SdKeys;
import io.vacco.summitdb.options.SdMSet;
import io.vacco.summitdb.options.SdSetEx;
import java.io.IOException;
import java.util.List;

public class SdKeyValue {

  public static long append(Redis r, String key, String value) throws IOException {
    return r.call("APPEND", key, value);
  }

  public static long dbSize(Redis r) throws IOException {
    return r.call("DBSIZE");
  }

  public static long del(Redis r, String ... keys) throws IOException {
    return r.call(SdBase.flatten(new Object[] {"DEL", keys}).toArray());
  }

  public static long exists(Redis r, String ... keys) throws IOException {
    return r.call(SdBase.flatten(new Object[] {"EXISTS", keys}).toArray());
  }

  public static long expire(Redis r, String key, long seconds) throws IOException {
    return r.call("EXPIRE", key, Long.toString(seconds)); // TODO why is summitdb not accepting a number primitive???
  }

  public static long expireAt(Redis r, String key, long timeStamp) throws IOException {
    return r.call("EXPIREAT", key, Long.toString(timeStamp));
  }

  public static String get(Redis r, String key) throws IOException {
    return SdBase.rawStrCmd(r, "GET", key);
  }

  public static List<String> keys(Redis r, SdKeys keys) throws IOException {
    return SdBase.toStringList(r.call(keys.toArgs()));
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

  public static long strLen(Redis r, String key) throws IOException {
    return r.call("STRLEN", key);
  }

  public static long ttl(Redis r, String key) throws IOException {
    return r.call("TTL", key);
  }
}
