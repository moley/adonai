package org.adonai.model;

import javafx.beans.property.SimpleStringProperty;
import org.adonai.Chord;
import org.adonai.fx.editcontent.KeyType;

/**
 * Created by OleyMa on 01.09.16.
 */
public class LinePart {

  private SimpleStringProperty text = new SimpleStringProperty();

  private SimpleStringProperty chord = new SimpleStringProperty();

  private SimpleStringProperty chordCapo = new SimpleStringProperty();

  private SimpleStringProperty originalChord = new SimpleStringProperty();

  public LinePart() {
    this.text.set(" ");
  }

  public LinePart(final Chord chord) {
    setChord(chord.toString());
    this.text.set(" ");
  }

  public String getChord(final KeyType keyType) {
    switch (keyType) {
    case CURRENT: return getChord();
    case ORIGINAL: return getOriginalChord();
    case CURRENT_CAPO: return getChordCapo();
    default:
      throw new IllegalStateException("Invalid type " + keyType);
    }
  }

  public LinePart(final String text) {
    setText(text);
  }

  public LinePart(final String text, final Chord chord) {
    setText(text);
    if (chord != null)
      setChord(chord.toString());
  }

  public LinePart(final String text, final String chord, final String chordCapo) {
    this(text, chord, chordCapo, null);
  }

  public LinePart(final String text, final String chord, final String chordCapo, final String originalChord) {
    setText(text);
    if (chord != null)
      setChord(chord);
    if (chordCapo != null)
      setChordCapo(chordCapo);
    if (originalChord != null)
      setOriginalChord(originalChord);
  }

  public String getChord() {
    return chord.get();
  }

  public String getOriginalChord() {
    return originalChord.get();
  }

  public void setChord(String chord) {
    this.chord.set(chord);
  }

  public void setOriginalChord(String originalChord) {
    this.originalChord.set(originalChord);
  }

  public String getText() {
    return text.get();
  }

  public void setText(String text) {
    this.text.set(text);
  }

  public SimpleStringProperty textProperty() {
    return text;
  }

  public SimpleStringProperty chordProperty() {
    return chord;
  }

  public SimpleStringProperty originalChordProperty() {
    return originalChord;
  }

  public String toString() {
    String linePartString = "";
    String chord = getChord();
    String text = getText();

    if (chord != null)
      linePartString += "(" + chord + ")";

    if (text != null && !text.isEmpty())
      linePartString += text;
    else
      linePartString += " ";

    return linePartString;
  }

  public String getChordCapo() {
    return chordCapo.get();
  }

  public SimpleStringProperty chordCapoProperty() {
    return chordCapo;
  }

  public void setChordCapo(String chordCapo) {
    this.chordCapo.set(chordCapo);
  }
}
