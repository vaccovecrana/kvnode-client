package io.vacco.kvnode.commands;

import io.vacco.kvnode.protocol.KnRedis;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class KnRaft {

  public static String raftAddPeer(KnRedis r, String peer) throws IOException {
    return KnBase.rawStrCmd(r, "RAFTADDPEER", peer);
  }

  public static String reftRemovePeer(KnRedis r, String peer) throws IOException {
    return KnBase.rawStrCmd(r, "RAFTREMOVEPEER", peer);
  }

  public static String raftLeader(KnRedis r) throws IOException {
    return KnBase.rawStrCmd(r, "RAFTLEADER");
  }

  public static String raftSnapshot(KnRedis r) throws IOException {
    return KnBase.rawStrCmd(r, "RAFTSNAPSHOT");
  }

  public static String raftState(KnRedis r) throws IOException {
    return KnBase.rawStrCmd(r, "RAFTSTATE");
  }

  public static Map<String, String> raftStats(KnRedis r) throws IOException {
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
