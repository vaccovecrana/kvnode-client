package io.vacco.redis;

import java.io.IOException;

/** Thrown whenever data could not be parsed. */
public class ProtocolException extends IOException {
  ProtocolException(String msg) {
    super(msg);
  }
}
