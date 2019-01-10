package org.adonai.model;

import org.junit.Assert;
import org.junit.Test;

public class SongTest {

  @Test
  public void additionals () {
    Additional additional = new Additional();
    additional.setLink("link");
    additional.setAdditionalType(AdditionalType.AUDIO);
    Song somesong = new Song();
    somesong.setAdditional(additional);
    somesong.setAdditional(additional);
    Assert.assertEquals (1, somesong.getAdditionals().size());
    Additional additional1 = somesong.findAdditional(AdditionalType.AUDIO);
    Assert.assertEquals(additional, additional1);

  }
}
