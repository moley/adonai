package org.adonai.model;

import java.io.File;

public class SongBuilder {

  private Song song = new Song();

  private SongPart currentSongPart;

  private Line currentLine;

  private LinePart currentLinePart;

  public static SongBuilder instance() {
    return new SongBuilder();
  }

  public SongBuilder withMp3 (final File file) {
    Additional additional = new Additional();
    additional.setAdditionalType(AdditionalType.AUDIO);
    additional.setLink(file.getAbsolutePath());
    additional.setCacheLink(file.getAbsolutePath());
    song.getAdditionals().add(additional);
    return this;
  }

  public SongBuilder withTitle (final String title) {
    song.setTitle(title);
    return this;
  }

  public SongBuilder withId (final String id) {
    song.setId(new Integer(id).intValue());
    return this;
  }

  public SongBuilder withPart (final SongPartType songPartType) {
    currentSongPart = new SongPart();
    currentSongPart.setSongPartType(songPartType);
    song.getSongParts().add(currentSongPart);
    return this;
  }

  public SongBuilder withPartId (final String id) {
    currentSongPart.setId(id);
    return this;
  }

  public SongBuilder withPartReference (final String referencedId) {
    currentSongPart = new SongPart();
    currentSongPart.setReferencedSongPart(referencedId);
    song.getSongParts().add(currentSongPart);
    return this;

  }

  public SongBuilder withPartReference (final String referencedId, final SongPartType partType) {
    currentSongPart = new SongPart();
    currentSongPart.setReferencedSongPart(referencedId);
    currentSongPart.setSongPartType(partType);
    song.getSongParts().add(currentSongPart);
    return this;

  }

  public SongBuilder withLine () {
    currentLine = new Line();
    currentSongPart.getLines().add(currentLine);

    return this;
  }

  public SongBuilder withLinePart (final String text, final String chord) {
    if (text == null)
      throw new IllegalArgumentException("Text must not be null, at least empty string");
    currentLinePart = new LinePart();
    currentLinePart.setText(text);
    currentLinePart.setChord(chord);
    currentLine.getLineParts().add(currentLinePart);

    return this;
  }

  public SongBuilder withLinePart (final String text, final String chord, final String originChord) {
    if (text == null)
      throw new IllegalArgumentException("Text must not be null, at least empty string");
    currentLinePart = new LinePart();
    currentLinePart.setText(text);
    currentLinePart.setChord(chord);
    currentLinePart.setOriginalChord(originChord);
    currentLine.getLineParts().add(currentLinePart);

    return this;
  }

  public Song get () {
    return song;
  }

  public SongBuilder withQuantity(int i) {
    currentSongPart.setQuantity(String.valueOf(i));
    return this;
  }
}
