package org.adonai;

public class SizeInfo {

  private final Double width;

  private final Double height;

  public SizeInfo (final Integer computeStringWidth, final Integer computeStringHeight) {
    if (computeStringWidth == null)
      throw new IllegalArgumentException("Argument computeStringWidth must not be null");
    if (computeStringHeight == null)
      throw new IllegalArgumentException("Argument computeStringHeight must not be null");

    this.height = new Double(computeStringHeight.doubleValue());
    this.width = new Double(computeStringWidth.doubleValue());
  }
  public SizeInfo (final Double computeStringWidth, final Double computeStringHeight) {
    if (computeStringWidth == null)
      throw new IllegalArgumentException("Argument computeStringWidth must not be null");
    if (computeStringHeight == null)
      throw new IllegalArgumentException("Argument computeStringHeight must not be null");

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
    return sizeinfoCompare.height.equals(height) && sizeinfoCompare.width.equals(width);
  }



}
