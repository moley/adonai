package org.adonai.ui.editor2;

import java.util.HashMap;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartType;

public class SongPartColorMap {

  private HashMap<SongPartType, String> colorMap = new HashMap<SongPartType, String>();
  private HashMap<SongPartType, String> colorMapSelected = new HashMap<SongPartType, String>();
  private final static String COLOR_DEFAULT = "#FFFFFF";
  private final static String COLOR_SELECTED_DEFAULT = "#DDDDDD";


  public SongPartColorMap () {
    //colorMap.put(SongPartType.REFRAIN, "#f7f6e9");
    //colorMap.put(SongPartType.VERS, "#fff6f5");
    //colorMapSelected.put(SongPartType.REFRAIN, "#fffef7");
    //colorMapSelected.put(SongPartType.VERS, "#fffafa");
  }

  public String getColorForPart(SongPart songPart) {

    String color = null;
    if (songPart != null && songPart.getSongPartType() != null) {
       color = colorMap.get(songPart.getSongPartType());
    }
    if (color == null)
      color = COLOR_DEFAULT;

    return color;
  }

  public String getColorSelectedForPart(SongPart songPart) {

    String color = null;
    if (songPart != null && songPart.getSongPartType() != null) {
      color = colorMapSelected.get(songPart.getSongPartType());
    }
    if (color == null)
      color = COLOR_SELECTED_DEFAULT;

    return color;
  }

}
