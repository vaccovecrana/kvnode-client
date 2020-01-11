package io.vacco.redis;

public class Failable {

  public static <T> T doTryRt(FailableSupplier<T> supplier) {
    try {
      return supplier.get();
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }
}
