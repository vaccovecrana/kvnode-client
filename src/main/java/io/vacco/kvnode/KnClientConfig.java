package io.vacco.kvnode;

public class KnClientConfig {

  public KnNode node;
  public int inputBufferSize = -1;
  public int outputBufferSize = -1;

  public static KnClientConfig of(String host, int port, int inputBufferSize, int outputBufferSize) {
    KnClientConfig c = new KnClientConfig();
    c.node = KnNode.from(host, port);
    c.inputBufferSize = inputBufferSize;
    c.outputBufferSize = outputBufferSize;
    return c;
  }

  public static KnClientConfig of(String host, int port) {
    return of(host, port, -1, -1);
  }
}
