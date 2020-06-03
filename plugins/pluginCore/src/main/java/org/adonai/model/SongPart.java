package org.adonai.model;

import javafx.beans.property.SimpleStringProperty;

import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by OleyMa on 01.09.16.
 */
public class SongPart {

  private String id;

  private String referencedSongPart;


  public SongPartType getSongPartType() {
    return songPartType;
  }

  @XmlTransient
  public String getSongPartTypeLabel () {
    return songPartType != null ? songPartType.name() : "UNDEFINED";
  }

  public void setSongPartType(SongPartType songPartType) {
    this.songPartType = songPartType;
  }

  private SongPartType songPartType;

  private List<Line> lines = new ArrayList<Line>();

  public List<Line> getLines() {
    return lines;
  }

  public void setLines(List<Line> lines) {
    this.lines = lines;
  }

  public boolean isEmpty () {
    return lines.isEmpty();
  }

  public void clear () {
    lines.clear();
    newLine();
  }

  public int getIndex (final Line line) {
    return getLines().indexOf(line);
  }

  public Line getPreviousLine (Line line) {
    int index = getIndex(line);
    return index > 0 ? getLines().get(index - 1): null;
  }

  public Line getNextLine (Line line) {
    int index = getIndex(line);
    return (index < getLines().size() - 1)? getLines().get(index + 1): null;
  }

  public Line getFirstLine () {
    return getLines().isEmpty() ? null: getLines().get(0);
  }

  public Line getLastLine () {
    return getLines().get(getLines().size() - 1);
  }

  public Line newLine () {
    LinePart linePart = new LinePart();
    linePart.setText(" ");

    Line line = new Line();
    line.getLineParts().add(linePart);
    getLines().add(line);
    return line;
  }

  public Line newLine (int index) {
    return newLine(index, true);
  }

  public Line newLine (int index, final boolean emptyLinePart) {

    Line line = new Line();
    if (emptyLinePart) {
      LinePart linePart = new LinePart();
      linePart.setText(" ");
      line.getLineParts().add(linePart);
    }
    getLines().add(index, line);
    return line;
  }

  @Override public String toString() {
    return "SongPart{" + "id='" + id + '\'' + ", songPartType=" + songPartType + ", lines=" + lines + "}\n";
  }

  public boolean hasText () {
    for (Line line: getLines()) {
      if (! line.getText().trim().isEmpty())
        return true;

    }

    return false;
  }

  public String getId() {
    if (id == null)
      id = UUID.randomUUID().toString();
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  @XmlTransient
  public String getEqualKey () {
    String equalKey = getSongPartType() + getLines().toString();
    return equalKey.replaceAll("\\s", "");

  }

}
