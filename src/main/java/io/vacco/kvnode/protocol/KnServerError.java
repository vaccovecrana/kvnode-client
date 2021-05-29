package io.vacco.kvnode.protocol;

import java.io.IOException;

/** Thrown whenever an error string is decoded. */
public class KnServerError extends IOException {
  public static final long serialVersionUID = 1;
  KnServerError(String msg) {
    super(msg);
  }
}
