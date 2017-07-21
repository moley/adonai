package org.adonai.model;

import org.junit.Assert;
import org.junit.Test;

public class SongPartTest {

  @Test
  public void clear () {
    SongPart songPart = new SongPart();
    songPart.getLines().add(new Line("Hello test"));
    songPart.clear();
    Assert.assertEquals (1, songPart.getLines().size());
    Assert.assertEquals (1, songPart.getLines().get(0).getLineParts().size());
    Assert.assertEquals (" ", songPart.getLines().get(0).getLineParts().get(0).getText());

  }

  @Test
  public void newLine () {
    SongPart songPart = new SongPart();
    songPart.getLines().add(new Line("Hello test"));
    songPart.newLine();

    Assert.assertEquals (2, songPart.getLines().size());
    Assert.assertEquals (1, songPart.getLines().get(0).getLineParts().size());
    Assert.assertEquals (1, songPart.getLines().get(1).getLineParts().size());
    Assert.assertEquals (" ", songPart.getLines().get(1).getLineParts().get(0).getText());

  }
}
