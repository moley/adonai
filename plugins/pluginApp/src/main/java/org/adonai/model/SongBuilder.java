package org.adonai.model;

import java.io.File;
import java.util.UUID;
import org.adonai.services.SongRepairer;

public class SongBuilder {

  private Song song = new Song();

  private SongPart currentSongPart;

  private SongStructItem currentSongStructItem;

  private Line currentLine;

  private LinePart currentLinePart;

  private boolean withRepairer = true;

  public static SongBuilder instance() {
    return new SongBuilder();
  }

  public SongBuilder disableRepairer () {
    this.withRepairer = false;
    return this;
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
    currentSongPart.setId(UUID.randomUUID().toString());

    currentSongStructItem = new SongStructItem();
    currentSongStructItem.setPartId(currentSongPart.getId());
    song.getSongParts().add(currentSongPart);
    song.getStructItems().add(currentSongStructItem);
    return this;
  }

  public SongBuilder withPartId (final String id) {
    currentSongPart.setId(id);
    currentSongStructItem.setPartId(id);
    return this;
  }

  public SongBuilder withPartReference (final String referencedId) {
    currentSongStructItem = new SongStructItem();
    currentSongStructItem.setPartId(referencedId);
    song.getStructItems().add(currentSongStructItem);
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
    if (withRepairer) {
      SongRepairer songRepairer = new SongRepairer();
      songRepairer.repairSong(song);
    }
    return song;
  }

  public SongBuilder withQuantity(int i) {
    currentSongStructItem.setQuantity(String.valueOf(i));
    return this;
  }

  public SongBuilder withRemarks (String remarks) {
    currentSongStructItem.setRemarks(remarks);
    return this;
  }
}
