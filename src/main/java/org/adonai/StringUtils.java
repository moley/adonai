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

  /**
   * leading whitespaces are trimmed, spaces right are untouched
   * @param untrimmed
   * @return
   */
  public final static String trimLeft (final String untrimmed) {
    return untrimmed.replaceAll("^\\s+","");
  }

  public final static String spaces( int spaces ) {
    return CharBuffer.allocate( spaces ).toString().replace( '\0', ' ' );
  }

  public final static String removeWhitespaces (final String withWhiteSpaces) {
    return withWhiteSpaces.replace(" ", "");
  }

  /**
   *
   * @param name
   * @return
   */
  public final static String getFirstUpper (final String name) {
    return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
  }

  public final static String getBracketContent (final String text) {
    int openingBracket = text.indexOf("(");
    int closingBracket = text.indexOf(")");
    if (openingBracket >= 0 && closingBracket >= 0)
      return text.substring(openingBracket + 1, closingBracket);
    else
      return "";
  }

  public final static String getNotNull (final String maybeNull) {
    return maybeNull != null ? maybeNull : "";
  }
}
