package org.adonai.ui.editor2;

import java.util.HashMap;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartType;

public class SongPartColorMap {

  private HashMap<SongPartType, String> colorMap = new HashMap<SongPartType, String>();
  private HashMap<SongPartType, String> colorMapSelected = new HashMap<SongPartType, String>();
  private final static String COLOR_DARKGREY = "#BBBBBB";
  private final static String COLOR_SELECTED_DARKGREY = "#DDDDDD";


  public SongPartColorMap () {
    colorMap.put(SongPartType.REFRAIN, "#BBBBEE");
    colorMap.put(SongPartType.VERS, "#EEBBBB");
    colorMapSelected.put(SongPartType.REFRAIN, "#CCCCFF");
    colorMapSelected.put(SongPartType.VERS, "#FFFFCC");
  }

  public String getColorForPart(SongPart songPart) {

    String color = null;
    if (songPart != null && songPart.getSongPartType() != null) {
       color = colorMap.get(songPart.getSongPartType());
      System.out.println("Determined color " + color + " for type " + songPart.getSongPartType());
    }
    if (color == null)
      color = COLOR_DARKGREY;

    return color;
  }

  public String getColorSelectedForPart(SongPart songPart) {

    String color = null;
    if (songPart != null && songPart.getSongPartType() != null) {
      color = colorMapSelected.get(songPart.getSongPartType());
      System.out.println("Determined color " + color + " for type " + songPart.getSongPartType());
    }
    if (color == null)
      color = COLOR_SELECTED_DARKGREY;

    return color;
  }

}
