package org.adonai;

public class LocationInfo {

  private Double x;

  private Double y;

  public LocationInfo (final LocationInfo locationInfo) {
    this.x = locationInfo.x;
    this.y = locationInfo.y;
  }

  public LocationInfo (final Double x, final Double y) {
    this.y = y;
    this.x = x;
  }

  public Double getX() {
    return x;
  }

  public Double getY() {
    return y;
  }

  public String toString () {return x.toString() + "-" + y.toString();}

}