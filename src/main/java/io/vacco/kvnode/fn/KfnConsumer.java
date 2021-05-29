package io.vacco.kvnode.fn;

@FunctionalInterface
public interface KfnConsumer<T> {
  void accept(T t) throws Exception;
}
