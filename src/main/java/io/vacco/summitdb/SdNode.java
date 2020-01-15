package io.vacco.summitdb;

public class SdNode {

  public String host;
  public int port;

  public static SdNode from(String host, int port) {
    SdNode n = new SdNode();
    n.host = host;
    n.port = port;
    return n;
  }
}
