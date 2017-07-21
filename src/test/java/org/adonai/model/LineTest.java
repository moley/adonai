package org.adonai.model;

import org.junit.Assert;
import org.junit.Test;

public class LineTest {

  @Test
  public void previousLinePart () {
    Line line = new Line();
    LinePart part1 = new LinePart();
    LinePart part2 = new LinePart();
    line.getLineParts().add(part1);
    line.getLineParts().add(part2);
    Assert.assertNull (line.getPreviousLinePart(part1));
    Assert.assertEquals (part1, line.getPreviousLinePart(part2));
  }

  @Test
  public void nextLinePart () {
    Line line = new Line();
    LinePart part1 = new LinePart();
    LinePart part2 = new LinePart();
    line.getLineParts().add(part1);
    line.getLineParts().add(part2);
    Assert.assertEquals (part2, line.getNextLinePart(part1));
    Assert.assertNull (line.getNextLinePart(part2));
  }

  @Test
  public void getText () {
    Line line = new Line();
    LinePart part1 = new LinePart("Hello ", "C");
    LinePart part2 = new LinePart("World", "F");
    line.getLineParts().add(part1);
    line.getLineParts().add(part2);
    Assert.assertEquals ("Hello World", line.getText());


  }
}
