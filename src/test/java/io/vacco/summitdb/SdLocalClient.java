package io.vacco.summitdb;

public class SdLocalClient {

  public static final SdClient sc = new SdClient("127.0.0.1", 7481);

  public static final void log(Object o) {
    System.out.println(o);
  }
}
