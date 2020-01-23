package io.vacco.summitdb.commands;

import io.vacco.redis.Redis;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SdRaft {

  public static String raftAddPeer(Redis r, String peer) throws IOException {
    return SdBase.rawStrCmd(r, "RAFTADDPEER", peer);
  }

  public static String reftRemovePeer(Redis r, String peer) throws IOException {
    return SdBase.rawStrCmd(r, "RAFTREMOVEPEER", peer);
  }

  public static String raftLeader(Redis r) throws IOException {
    return SdBase.rawStrCmd(r, "RAFTLEADER");
  }

  public static String raftSnapshot(Redis r) throws IOException {
    return SdBase.rawStrCmd(r, "RAFTSNAPSHOT");
  }

  public static String raftState(Redis r) throws IOException {
    return SdBase.rawStrCmd(r, "RAFTSTATE");
  }

  public static Map<String, String> raftStats(Redis r) throws IOException {
    Map<String, String> result = new TreeMap<>();
    List<byte[]> raw = r.call("RAFTSTATS");
    for (int i = 0; i < raw.size(); i = i + 2) {
      String stat = new String(raw.get(i));
      String val = new String(raw.get(i + 1));
      result.put(stat, val);
    }
    return result;
  }

}
