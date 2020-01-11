package io.vacco.redis;

public interface FailableFn<I, O> {
  O apply(I i) throws Exception;
}
