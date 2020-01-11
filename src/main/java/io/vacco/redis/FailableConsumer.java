package io.vacco.redis;

@FunctionalInterface
public interface FailableConsumer<T> {
  void accept(T t) throws Exception;
}
