package io.vacco.summitdb;

import io.vacco.kvnode.KnClient;

public class KnLocalClient {

  public static final KnClient sc = new KnClient("127.0.0.1", 6379);

  public static void log(Object o) {
    System.out.println(o);
  }
}
