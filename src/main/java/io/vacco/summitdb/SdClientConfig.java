package io.vacco.summitdb;

public class SdClientConfig {

  public SdNode node;
  public int inputBufferSize = -1;
  public int outputBufferSize = -1;

  public static SdClientConfig of(String host, int port, int inputBufferSize, int outputBufferSize) {
    SdClientConfig c = new SdClientConfig();
    c.node = SdNode.from(host, port);
    c.inputBufferSize = inputBufferSize;
    c.outputBufferSize = outputBufferSize;
    return c;
  }

  public static SdClientConfig of(String host, int port) {
    return of(host, port, -1, -1);
  }
}
