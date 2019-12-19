package org.adonai;

public class AreaInfo {

  private final LocationInfo location;
  private final SizeInfo size;


  public AreaInfo (final LocationInfo location, final SizeInfo size) {
    this.location = location;
    this.size = size;
  }
  
  public Double getX () {
    return location.getX();
  }

  public Double getY () {
    return location.getY();
  }

  public Double getWidth () {
    return size.getWidth();
  }

  public Double getHeight () {
    return size.getHeight();
  }

  public String toString () {
    return "X:" + location.getX() + "- Y:" + location.getY() + "- Width: " + size.getWidth() + "- Height: " + size.getHeight();
  }


}