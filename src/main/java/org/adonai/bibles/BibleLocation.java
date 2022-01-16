package org.adonai.bibles;

public class BibleLocation {

  private String asString;

  /**
   * z.B. #LUTHER_1912,1.Mose,1 12
   * @param asString
   */
  public BibleLocation (String asString) {
    this.asString = asString;
  }

  public String toString () {
    return asString;
  }


}
