package org.adonai.model;

import javafx.beans.property.SimpleStringProperty;
import org.adonai.Chord;

/**
 * Created by OleyMa on 01.09.16.
 */
public class LinePart {

  private SimpleStringProperty text = new SimpleStringProperty();

  private SimpleStringProperty chord = new SimpleStringProperty();

  public LinePart () {

  }

  public LinePart (final Chord chord) {
    setChord(chord.toString());
    this.text.set(" ");
  }

  public LinePart (final String text) {
    setText(text);
  }
  public LinePart (final String text, final Chord chord)  {
    setText(text);
    if (chord != null)
      setChord(chord.toString());
  }

  public LinePart (final String text, final String chord)  {
    setText(text);
    if (chord != null)
      setChord(chord);
  }

  public String getChord() {
    return chord.get();
  }

  public void setChord(String chord) {
    this.chord.set(chord);

  }

  public String getText() {
    return text.get();
  }


  public void setText(String text) {
    this.text.set(text);
  }

  public SimpleStringProperty textProperty () {
    return text;
  }

  public SimpleStringProperty chordProperty () {
    return chord;
  }

  public String toString () {
    String linePartString ="";
    String chord = getChord();
    String text = getText();

    if (chord != null)
      linePartString+= "(" + chord + ")";

    if (text != null && ! text.isEmpty())
      linePartString += text;
    else
      linePartString += "_";

    return linePartString;
  }
}
