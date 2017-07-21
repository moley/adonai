package org.adonai;

public class ColorInfo {

  private final int red;

  private final int green;

  private final int blue;

  public final static ColorInfo BLACK = new ColorInfo (0, 0, 0);
  public final static ColorInfo WHITE = new ColorInfo (255, 255, 255);

  public ColorInfo (final String colorAsString) throws IllegalStateException {
    String [] colorAspects = colorAsString != null ? colorAsString.split("x") : new String [0];
    if (colorAspects.length != 3)
      throw new IllegalStateException(colorAsString);

    this.green = Integer.valueOf(colorAspects [0]);
    this.red = Integer.valueOf(colorAspects [1]);
    this.blue = Integer.valueOf(colorAspects [2]);
  }

  public ColorInfo (int red, int green, int blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  public String toString () {
    return getGreen() + "x" + getRed() + "x" + getBlue();
  }

  public int getBlue() {
    return blue;
  }

  public int getGreen() {
    return green;
  }

  public int getRed() {
    return red;
  }

  public boolean equals (Object object) {
    if (! (object instanceof ColorInfo))
      return false;

    ColorInfo compareObject = (ColorInfo) object;

    return compareObject.getGreen() == green && compareObject.getRed() == red && compareObject.getBlue() == blue;
  }

}