package io.vacco.kvnode.fn;

@FunctionalInterface
public interface KfnSupplier<T> {

  T get() throws Exception;

  static <T> T tryGet(KfnSupplier<T> sup) {
    try {
      return sup.get();
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }
}