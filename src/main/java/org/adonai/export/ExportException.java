package org.adonai.export;

public class ExportException extends Exception {

  public ExportException (final Throwable t) {
    super (t);
  }

  public ExportException (final String message, final Throwable t) {
    super (message, t);
  }
}
