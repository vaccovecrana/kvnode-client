package io.vacco.summitdb.commands;

import io.vacco.redis.Redis;
import io.vacco.summitdb.options.*;
import io.vacco.summitdb.options.SdIndexMetadata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SdIndex {

  public static long delIndex(Redis r, String index) throws IOException {
    return r.call("DELINDEX", index);
  }

  public static List<SdIndexMetadata> indexes(Redis r, SdIndexes indexes) throws IOException {
    List<Object> raw = r.call(indexes.toArgs());
    List<SdIndexMetadata> metadata = new ArrayList<>();
    if (indexes.isDetails()) {
      for (int i = 0; i < raw.size(); i = i + 3) {
        byte[] bName = (byte[]) raw.get(i);
        byte[] bPath = (byte[]) raw.get(i + 1);
        byte[] bDetails = ((byte[]) ((List) ((List) raw.get(i + 2)).get(0)).get(0));
        metadata.add(SdIndexMetadata.from(
            new String(bName), new String(bPath), new String(bDetails)
        ));
      }
      return metadata;
    }
    return raw.stream().map(o -> (byte[]) o).map(String::new)
        .map(st0 -> SdIndexMetadata.from(st0, null, null))
        .collect(Collectors.toList());
  }

  public static List<String> iter(Redis r, SdIter iter) throws IOException {
    return SdBase.toStringList(r.call(iter.toArgs()));
  }

  public static List<String> iter(Redis r, String indexName) throws IOException {
    return iter(r, new SdIter().index(indexName));
  }

  public static List<String> rect(Redis r, SdRect rect) throws IOException {
    List<byte[]> raw = r.call(rect.toArgs());
    return raw.stream().map(String::new).collect(Collectors.toList());
  }

  public static String setIndex(Redis r, SdSetIndexText indexText) throws IOException {
    return SdBase.rawStrCmd(r, indexText.toArgs());
  }

  public static String setIndexText(Redis r, String name, String pattern) throws IOException {
    return setIndex(r, new SdSetIndexText().namePattern(
        new SdSetIndexNamePattern().name(name).pattern(pattern)
    ));
  }

  public static String setIndex(Redis r, SdSetIndexNumber indexNumber) throws IOException {
    return SdBase.rawStrCmd(r, indexNumber.toArgs());
  }

  public static String setIndexNumber(Redis r, String name, String pattern, SdSetIndexNumber.Type t) throws IOException {
    return setIndex(r, new SdSetIndexNumber().namePattern(
        new SdSetIndexNamePattern().name(name).pattern(pattern)
    ).type(t));
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

  public static String setIndexSpatial(Redis r, String name, String pattern) throws IOException {
    return setIndex(r, new SdSetIndexSpatial().namePattern(
        new SdSetIndexNamePattern().name(name).pattern(pattern)
    ));
  }
}
