package io.vacco.kvnode;

public class KnNode {

  public String host;
  public int port;

  public static KnNode from(String host, int port) {
    KnNode n = new KnNode();
    n.host = host;
    n.port = port;
    return n;
  }
}
