package io.vacco.kvnode.protocol;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/** Helper class for pipelining. */
public class KnPipeline {

  private int n = 0;
  private final KnEncoder encoder;
  private final KnParser parser;

  public KnPipeline(KnEncoder encoder, KnParser parser) {
    this.encoder = Objects.requireNonNull(encoder);
    this.parser = Objects.requireNonNull(parser);
  }

  /**
   * Write a new command to the server.
   *
   * @param args Command and arguments.
   * @return self for chaining
   * @throws IOException Propagated from underlying server.
   */
  public KnPipeline call(String... args) throws IOException {
    encoder.write(Arrays.asList((Object[]) args));
    encoder.flush();
    n++;
    return this;
  }

  /**
   * Returns an aligned list of responses for each of the calls.
   *
   * @return The responses
   * @throws IOException Propagated from underlying server.
   */
  public List<Object> read() throws IOException {
    List<Object> ret = new LinkedList<>();
    while (n-- > 0) {
      ret.add(parser.parse());
    }
    return ret;
  }
}
