package org.adonai;

public class AreaInfo {

  private final LocationInfo location;
  private final SizeInfo size;


  public AreaInfo (final LocationInfo location, final SizeInfo size) {
    this.location = location;
    this.size = size;
  }

  public AreaInfo(final Double x, final Double y, final SizeInfo size) {
    this.location = new LocationInfo(x, y);
    this.size = size;
  }

  public SizeInfo getSize() {
    return size;
  }

  public LocationInfo getLocation() {
    return location;
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
    return location.getX() + "-" + location.getY() + "-" + size.getWidth() + "-" + size.getHeight();
  }


}