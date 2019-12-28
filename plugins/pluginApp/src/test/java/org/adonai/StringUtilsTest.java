package org.adonai;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilsTest {

  @Test
  public void getNotNull () {

    Assert.assertEquals ("", StringUtils.getNotNull(null));
    Assert.assertEquals ("", StringUtils.getNotNull(""));
    Assert.assertEquals ("Hello", StringUtils.getNotNull("Hello"));

  }

  @Test
  public void getBracketContent () {
    Assert.assertEquals ("2x", StringUtils.getBracketContent("Hello(2x)"));

  }
}
