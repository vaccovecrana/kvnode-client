package io.vacco.kvnode.fn;

@FunctionalInterface
public interface Kfn<I, O> {
  O apply(I i) throws Exception;
}