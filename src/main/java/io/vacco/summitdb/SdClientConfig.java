package io.vacco.summitdb;

public class SdClientConfig {

  public String host;
  public int port;
  public int inputBufferSize = -1;
  public int outputBufferSize = -1;

  public static SdClientConfig of(String host, int port, int inputBufferSize, int outputBufferSize) {
    SdClientConfig c = new SdClientConfig();
    c.host = host;
    c.port = port;
    c.inputBufferSize = inputBufferSize;
    c.outputBufferSize = outputBufferSize;
    return c;
  }

  public static SdClientConfig of(String host, int port) {
    return of(host, port, -1, -1);
  }
}
