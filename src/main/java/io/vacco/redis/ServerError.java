package io.vacco.redis;

import java.io.IOException;

/** Thrown whenever an error string is decoded. */
public class ServerError extends IOException {
  ServerError(String msg) {
    super(msg);
  }
}
