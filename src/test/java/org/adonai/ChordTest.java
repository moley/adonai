package org.adonai;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by OleyMa on 11.08.16.
 */
public class ChordTest {

  @Test
  public void add9 () {
    Chord chord = new Chord("Cadd9");
    Assert.assertEquals ("Cadd9", chord.toString());

  }
  @Test
  public void bbDoesntGetH () {
    Chord chord = new Chord("Bb");
    Assert.assertEquals ("Bb", chord.toString());

  }

  @Test
  public void bGetsH () {
    Chord chord = new Chord("B");
    Assert.assertEquals ("H", chord.toString());

  }


  @Test
  public void fismoll () {
    Chord chord = new Chord("F#m");
    Assert.assertEquals ("F#m", chord.toString());

  }

  @Test
  public void mollWithM () throws InvalidChordException {
    Chord chord = new Chord("Am7/C");
  }

  @Test
  public void susChord () throws InvalidChordException {
    Chord chord = new Chord ("Asus");

  }

  @Test
  public void normalChord () throws InvalidChordException {
    Chord chord = new Chord ("A");

  }

  @Test
  public void mollSeven () throws InvalidChordException {
    Chord chord = new Chord ("e7");

  }

  @Test
  public void moll () throws InvalidChordException {
    Chord chord = new Chord ("f#");

  }

  @Test(expected = InvalidChordException.class)
  public void slashAsChord () throws InvalidChordException {
    Chord chord = new Chord ("/");

  }

  @Test
  public void incrementedChord () throws InvalidChordException {
    Chord chord = new Chord ("A#");
  }

  @Test(expected = InvalidChordException.class)
  public void novalidChord () throws InvalidChordException {
    Chord chord = new Chord ("s");
  }

  @Test
  public void decrementedChord () throws InvalidChordException {
    Chord chord = new Chord ("Ab");
  }

  @Test
  public void chordWithBase () throws InvalidChordException {
    Chord chord = new Chord ("G/H");
  }

  @Test
  public void majChord () throws InvalidChordException {
    Chord chord = new Chord ("Gmaj");
  }

  @Test
  public void sextChord () throws InvalidChordException {
    Chord chord = new Chord ("G6/H");
  }

  @Test(expected = InvalidChordException.class)
  public void invalidChord () throws InvalidChordException {
    Chord chord = new Chord ("Dies");
  }

  @Test
  public void transposeUp () {
    Chord chord = new Chord ("D/F#");
    chord.transpose(2, NoteEntryType.INCREMENT);
    Assert.assertEquals ("E/G#", chord.toString());
  }

  @Test
  public void transposeDown () {
    Chord chord = new Chord ("D/F#");
    chord.transpose(-2, NoteEntryType.INCREMENT);
    Assert.assertEquals ("C/E", chord.toString());

  }

  @Test
  public void transposeNull () {
    Chord chord = new Chord ("D/F#");
    chord.transpose(0, NoteEntryType.INCREMENT);
    Assert.assertEquals ("D/F#", chord.toString());

  }

  @Test
  public void transposeToFismoll () {
    Chord chord = new Chord ("Em7");
    chord.transpose(2, NoteEntryType.INCREMENT);
    Assert.assertEquals ("F#m7", chord.toString());

  }

  @Test
  public void transposeUpBorder ( ){
    Chord chord = new Chord ("A");
    chord.transpose(4, NoteEntryType.INCREMENT);
    Assert.assertEquals ("C#", chord.toString());

  }

  @Test
  public void transposeDownBorder () {
    Chord chord = new Chord ("C");
    chord.transpose(-1, NoteEntryType.INCREMENT);
    Assert.assertEquals ("H", chord.toString());

  }
}
