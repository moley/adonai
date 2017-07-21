package org.adonai;

import java.nio.CharBuffer;

public class StringUtils {

  /**
   * trims a string only right, leading whitespaces are untouched
   * @param untrimmed
   * @return
   */
  public final static String trimRight (final String untrimmed) {
    return untrimmed.replaceAll("\\s+$", "");
  }

  public final static String spaces( int spaces ) {
    return CharBuffer.allocate( spaces ).toString().replace( '\0', ' ' );
  }
}
