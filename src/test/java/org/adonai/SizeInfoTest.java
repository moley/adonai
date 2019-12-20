package org.adonai;

import org.junit.Assert;
import org.junit.Test;

public class SizeInfoTest {


  @Test(expected = IllegalArgumentException.class)
  public void intConstructorArg1Null () {
    new SizeInfo (null, Integer.valueOf(1) );
  }

  @Test(expected = IllegalArgumentException.class)
  public void intConstructorArg2Null () {
    new SizeInfo (Integer.valueOf(1), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void doubleConstructorArg1Null () {
    new SizeInfo (null, Double.valueOf(1) );
  }

  @Test(expected = IllegalArgumentException.class)
  public void doubleConstructorArg2Null () {
    new SizeInfo (Double.valueOf(1), null);
  }

  @Test
  public void equals () {
    SizeInfo sizeInfo = new SizeInfo(0, 0);
    Assert.assertNotEquals(sizeInfo, "");

  }


}
