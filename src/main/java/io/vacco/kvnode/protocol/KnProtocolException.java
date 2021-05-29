package io.vacco.kvnode.protocol;

import java.io.IOException;

/** Thrown whenever data could not be parsed. */
public class KnProtocolException extends IOException {
  public static final long serialVersionUID = 1;
  KnProtocolException(String msg) {
    super(msg);
  }
}
