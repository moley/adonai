package org.adonai;

public class SizeInfo {

  private final Double width;

  private final Double height;

  public SizeInfo (final Integer computeStringWidth, final Integer computeStringHeight) {
    this.height = new Double(computeStringHeight.doubleValue());
    this.width = new Double(computeStringWidth.doubleValue());
  }
  public SizeInfo (final Double computeStringWidth, final Double computeStringHeight) {
    this.height = computeStringHeight;
    this.width = computeStringWidth;
  }

  public Double getWidth() {
    return width;
  }

  public int getWidthAsInt () {
    return width.intValue();
  }

  public int getHeightAsInt () {
    return height.intValue();
  }

  public Double getHeight() {
    return height;
  }

  public String toString () {
    return width + "x" + height;
  }

  public boolean equals (final Object object) {
    if (!(object instanceof SizeInfo))
      return false;
    SizeInfo sizeinfoCompare = (SizeInfo) object;
    return sizeinfoCompare.height == height && sizeinfoCompare.width == width;
  }



}
