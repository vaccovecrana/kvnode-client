package io.vacco.kvnode.commands;

import io.vacco.kvnode.protocol.KnRedis;
import io.vacco.kvnode.options.KnKeys;
import io.vacco.kvnode.options.KnMSet;
import io.vacco.kvnode.options.KnSetEx;
import java.io.IOException;
import java.util.List;

public class KnKeyValue {

  public static long append(KnRedis r, String key, String value) throws IOException {
    return r.call("APPEND", key, value);
  }

  public static long dbSize(KnRedis r) throws IOException {
    return r.call("DBSIZE");
  }

  public static long del(KnRedis r, String ... keys) throws IOException {
    return r.call(KnBase.flatten(new Object[] {"DEL", keys}).toArray());
  }

  public static long exists(KnRedis r, String ... keys) throws IOException {
    return r.call(KnBase.flatten(new Object[] {"EXISTS", keys}).toArray());
  }

  public static long expire(KnRedis r, String key, long seconds) throws IOException {
    return r.call("EXPIRE", key, Long.toString(seconds)); // TODO why is summitdb not accepting a number primitive???
  }

  public static long expireAt(KnRedis r, String key, long timestampSeconds) throws IOException {
    return r.call("EXPIREAT", key, Long.toString(timestampSeconds));
  }

  public static String get(KnRedis r, String key) throws IOException {
    return KnBase.rawStrCmd(r, "GET", key);
  }

  public static List<String> keys(KnRedis r, KnKeys keys) throws IOException {
    return KnBase.toStringList(r.call(keys.toArgs()));
  }

  public static String mset(KnRedis r, KnMSet mSet) throws IOException {
    return KnBase.rawStrCmd(r, mSet.toArgs());
  }

  public static long pExpire(KnRedis r, String key, long millis) throws IOException {
    return r.call("PEXPIRE", key, Long.toString(millis));
  }

  public static long pExpireAt(KnRedis r, String key, String unsignedMillis) throws IOException {
    return r.call("PEXPIREAT", key, unsignedMillis);
  }

  public static long pExpireAt(KnRedis r, String key, long timestampMillis) throws IOException {
    return pExpireAt(r, key, Long.toString(timestampMillis));
  }

  public static long pTtl(KnRedis r, String key) throws IOException {
    return r.call("PTTL", key);
  }

  public static String set(KnRedis r, String key, String value) throws IOException {
    return KnBase.rawStrCmd(r, "SET", key, value);
  }

  public static String set(KnRedis r, KnSetEx set) throws IOException {
    return KnBase.rawStrCmd(r, set.toArgs());
  }

  public static long strLen(KnRedis r, String key) throws IOException {
    return r.call("STRLEN", key);
  }

  public static long ttl(KnRedis r, String key) throws IOException {
    return r.call("TTL", key);
  }
}
