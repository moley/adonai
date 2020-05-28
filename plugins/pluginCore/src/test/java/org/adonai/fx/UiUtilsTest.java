package org.adonai.fx;

import org.junit.Test;

public class UiUtilsTest {

  @Test(expected = IllegalArgumentException.class)
  public void getBoundsNull () {
    UiUtils.getBounds(null);

  }
}
