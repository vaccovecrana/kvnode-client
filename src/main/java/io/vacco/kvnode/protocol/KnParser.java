package io.vacco.kvnode.protocol;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/** Implements the parser (reader) side of protocol. */
public class KnParser implements Closeable {

  /** The input stream used to read the data from. */
  private final InputStream input;

  /**
   * Constructor.
   *
   * @param input The stream to read the data from.
   */
  KnParser(InputStream input) {
    this.input = input;
  }

  /**
   * Parse incoming data from the stream.
   * <p>
   * Based on each of the markers which will identify the type of data being sent, the parsing
   * is delegated to the type-specific methods.
   *
   * @return The parsed object
   * @throws IOException       Propagated from the stream
   * @throws KnProtocolException In case unexpected bytes are encountered.
   */
  Object parse() throws IOException, KnProtocolException {
    Object ret;
    int read = this.input.read();
    switch (read) {
      case '+':
        ret = this.parseSimpleString();
        break;
      case '-':
        throw new KnServerError(new String(this.parseSimpleString()));
      case ':':
        ret = this.parseNumber();
        break;
      case '$':
        ret = this.parseBulkString();
        break;
      case '*':
        long len = this.parseNumber();
        if (len == -1) {
          ret = null;
        } else {
          List<Object> arr = new LinkedList<>();
          for (long i = 0; i < len; i++) {
            arr.add(this.parse());
          }
          ret = arr;
        }
        break;
      case -1:
        return null;
      default:
        throw new KnProtocolException("Unexpected input: " + (byte) read);
    }

    return ret;
  }

  /**
   * Parse "RESP Bulk string" as a String object.
   *
   * @return The parsed response
   * @throws IOException Propagated from underlying stream.
   */
  private byte[] parseBulkString() throws IOException, KnProtocolException {
    final long expectedLength = parseNumber();
    if (expectedLength == -1) {
      return null;
    }
    if (expectedLength > Integer.MAX_VALUE) {
      throw new KnProtocolException("Unsupported value length for bulk string");
    }
    final int numBytes = (int) expectedLength;
    final byte[] buffer = new byte[numBytes];
    int read = 0;
    while (read < expectedLength) {
      read += input.read(buffer, read, numBytes - read);
    }
    if (input.read() != '\r') {
      throw new KnProtocolException("Expected CR");
    }
    if (input.read() != '\n') {
      throw new KnProtocolException("Expected LF");
    }

    return buffer;
  }

  /**
   * Parse "RESP Simple String"
   *
   * @return Resultant string
   * @throws IOException Propagated from underlying stream.
   */
  private byte[] parseSimpleString() throws IOException {
    return scanCr(1024);
  }

  private long parseNumber() throws IOException {
    return Long.parseLong(new String(scanCr(1024)));
  }

  private byte[] scanCr(int size) throws IOException {
    int idx = 0;
    int ch;
    byte[] buffer = new byte[size];
    while ((ch = input.read()) != '\r') {
      buffer[idx++] = (byte) ch;
      if (idx == size) {
        // increase buffer size.
        size *= 2;
        buffer = java.util.Arrays.copyOf(buffer, size);
      }
    }
    if (input.read() != '\n') {
      throw new KnProtocolException("Expected LF");
    }
    return Arrays.copyOfRange(buffer, 0, idx);
  }

  @Override public void close() {
    try { input.close(); }
    catch (IOException e) { throw new IllegalStateException(e); }
  }
}
