package io.vacco.redis;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

/**
 * A lightweight implementation of the Redis server protocol at https://redis.io/topics/protocol
 * <p>
 * Effectively a complete Redis client implementation.
 */
public class Redis implements Closeable {

  /** Used for writing the data to the server. */
  private final Encoder writer;
  /** Used for reading responses from the server. */
  private final Parser reader;

  /**
   * Construct the connection with the specified Socket as the server connection with default buffer sizes.
   *
   * @param socket Connected socket to the server.
   * @throws IOException If a socket error occurs.
   */
  public Redis(Socket socket) throws IOException {
    this(socket, 1 << 16, 1 << 16);
  }

  /**
   * Construct the connection with the specified Socket as the server connection with specified buffer sizes.
   *
   * @param socket           Socket to connect to
   * @param inputBufferSize  buffer size in bytes for the input stream
   * @param outputBufferSize buffer size in bytes for the output stream
   * @throws IOException If a socket error occurs.
   */
  public Redis(Socket socket, int inputBufferSize, int outputBufferSize) throws IOException {
    this(
        new BufferedInputStream(socket.getInputStream(), inputBufferSize),
        new BufferedOutputStream(socket.getOutputStream(), outputBufferSize)
    );
  }

  /**
   * Construct with the specified streams to respectively read from and write to.
   *
   * @param inputStream  Read from this stream
   * @param outputStream Write to this stream
   */
  public Redis(InputStream inputStream, OutputStream outputStream) {
    this.reader = new Parser(inputStream);
    this.writer = new Encoder(outputStream);
  }

  /**
   * Execute a Redis command and return it's result.
   *
   * @param args Command and arguments to pass into redis.
   * @param <T>  The expected result type
   * @return Result of redis.
   * @throws IOException All protocol and io errors are IO exceptions.
   */
  public <T> T call(Object... args) throws IOException {
    writer.write(Arrays.asList(args));
    writer.flush();
    return read();
  }

  /**
   * Does a blocking read to wait for redis to send data.
   *
   * @param <T> The expected result type.
   * @return Result of redis
   * @throws IOException Propagated
   */
  public <T> T read() throws IOException {
    return (T) reader.parse();
  }

  /**
   * Create a pipeline which writes all commands to the server and only starts
   * reading the response when read() is called.
   *
   * @return A pipeline object.
   */
  public Pipeline pipeline() {
    return new Pipeline(writer, reader);
  }

  @Override
  public void close() {
    writer.close();
    reader.close();
  }
}
