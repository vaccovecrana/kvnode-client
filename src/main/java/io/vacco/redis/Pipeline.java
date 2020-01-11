package io.vacco.redis;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/** Helper class for pipelining. */
public class Pipeline {

  private int n = 0;
  private final Encoder encoder;
  private final Parser parser;

  public Pipeline(Encoder encoder, Parser parser) {
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
  public Pipeline call(String... args) throws IOException {
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
