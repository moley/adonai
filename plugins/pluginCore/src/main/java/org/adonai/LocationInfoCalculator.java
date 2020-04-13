package org.adonai;

public class LocationInfoCalculator {

  public LocationInfo addX (final LocationInfo locationInfo, Double x) {
    return new LocationInfo(locationInfo.getX() + x, locationInfo.getY());
  }

  public LocationInfo addY (final LocationInfo locationInfo, Double y) {
    return new LocationInfo(locationInfo.getX(), locationInfo.getY() + y);
  }
}
