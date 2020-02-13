package org.adonai.export;

import org.adonai.AreaInfo;
import org.adonai.model.Song;

public class ExportToken {

  private String text;

  private AreaInfo areaInfo;

  private ExportTokenType exportTokenType;

  private final Song song;


  public ExportToken(final Song song, final String text, final AreaInfo areaInfo, final ExportTokenType exportTokenType) {
    this.song = song;
    this.text = text;
    this.areaInfo = areaInfo;
    this.exportTokenType = exportTokenType;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }


  public AreaInfo getAreaInfo() {
    return areaInfo;
  }

  public void setAreaInfo(AreaInfo areaInfo) {
    this.areaInfo = areaInfo;
  }

  public ExportTokenType getExportTokenType () {
    return exportTokenType;
  }


  public String toString () {
    return exportTokenType.name() + "    - " + text + "(" + areaInfo + ")";

  }

  public Song getSong() {
    return song;
  }
}
