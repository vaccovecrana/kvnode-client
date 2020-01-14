package io.vacco.summitdb.commands;

import io.vacco.redis.Redis;
import io.vacco.summitdb.options.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class SdIndex {

  public static List<String> iter(Redis r, SdIter iter) throws IOException {
    List<byte[]> raw = r.call(iter.toArgs());
    return raw.stream().map(String::new).collect(Collectors.toList());
  }

  public static String setIndex(Redis r, SdSetIndexText indexText) throws IOException {
    return SdBase.rawStrCmd(r, indexText.toArgs());
  }

  public static String setIndex(Redis r, SdSetIndexNumber indexNumber) throws IOException {
    return SdBase.rawStrCmd(r, indexNumber.toArgs());
  }

  public static String setIndex(Redis r, SdSetIndexJson indexJson) throws IOException {
    return SdBase.rawStrCmd(r, indexJson.toArgs());
  }

  public static String setIndex(Redis r, SdSetIndexUser indexUser) throws IOException {
    return SdBase.rawStrCmd(r, indexUser.toArgs());
  }

  public static String setIndex(Redis r, SdSetIndexSpatial indexSpatial) throws IOException {
    return SdBase.rawStrCmd(r, indexSpatial.toArgs());
  }
}
