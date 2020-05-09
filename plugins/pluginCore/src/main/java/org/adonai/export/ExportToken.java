package org.adonai.export;

import org.adonai.AreaInfo;
import org.adonai.model.Song;
import org.adonai.model.SongStructItem;

public class ExportToken {

  private String text;

  private AreaInfo areaInfo;

  private ExportTokenType exportTokenType;

  private final Song song;

  private final SongStructItem songStructItem;


  public ExportToken(final Song song, final SongStructItem songStructItem, final String text, final AreaInfo areaInfo, final ExportTokenType exportTokenType) {
    this.song = song;
    this.text = text;
    this.areaInfo = areaInfo;
    this.exportTokenType = exportTokenType;
    this.songStructItem = songStructItem;
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
    return exportTokenType.name() + "    - " + text + "(" + areaInfo + ")\n";

  }

  public Song getSong() {
    return song;
  }

  public SongStructItem getSongStructItem() {
    return songStructItem;
  }
}
