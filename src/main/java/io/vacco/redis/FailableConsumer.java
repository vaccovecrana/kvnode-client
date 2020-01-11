package io.vacco.redis;

@FunctionalInterface
public interface FailableConsumer<T, E extends Throwable> {
  void accept(T t) throws E;
}
