package io.vacco.redis;

@FunctionalInterface
public interface FailableSupplier<T>  {
  T get() throws Exception;
}
