package org.adonai.model;

public class SongBuilder {

  private Song song = new Song();

  private SongPart currentSongPart;

  private Line currentLine;

  private LinePart currentLinePart;

  public static SongBuilder instance() {
    return new SongBuilder();
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

  public Song get () {
    return song;
  }





}
