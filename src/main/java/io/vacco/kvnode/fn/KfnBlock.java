package io.vacco.kvnode.fn;

@FunctionalInterface
public interface KfnBlock {
  void run() throws Exception;
}