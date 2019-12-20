package org.adonai.ui.editor2;

import org.adonai.model.SongPart;

public class SongPartColorMap {

  private final static String COLOR_DEFAULT = "#FFFFFF";
  private final static String COLOR_SELECTED_DEFAULT = "#DDDDDD";


  public String getColorForPart(SongPart songPart) {
    return COLOR_DEFAULT;
  }

  public String getColorSelectedForPart(SongPart songPart) {
    return COLOR_SELECTED_DEFAULT;
  }

}
