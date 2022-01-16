package org.adonai;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

  public String getTimeAsString(LocalTime localTime) {
    if (localTime == null)
      return "";

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    return localTime.format(formatter);
  }
}
