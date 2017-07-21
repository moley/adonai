package org.adonai.export;

import org.adonai.model.SongBuilder;
import org.adonai.model.SongPartType;
import org.adonai.model.Song;

import java.util.ArrayList;
import java.util.List;

public class AbstractExportTest {

  public final Song getSong1() {
    SongBuilder builder = SongBuilder.instance();
    builder = builder.withId("1").withTitle("Everlasting God");
    builder = builder.withPart(SongPartType.VERS).withLine();
    builder = builder.withLinePart("Strength will rise as we ", "G");
    builder = builder.withLinePart("wait upon the ", "Gsus");
    builder = builder.withLine().withLinePart("Lord, we will ", "G");
    builder = builder.withLinePart("wait upon the ", "Gsus");
    builder = builder.withLinePart("Lord", "G");

    builder = builder.withPart(SongPartType.REFRAIN).withLine();
    builder = builder.withLinePart("Our ", "G/H");
    builder = builder.withLinePart("God, ", "C");
    builder = builder.withLine().withLinePart("you ", "G/H");
    builder = builder.withLinePart("reign ", "C");
    builder = builder.withLinePart("for", "D");
    builder = builder.withLinePart("e", "Em");
    builder = builder.withLinePart("ver", "D");
    return builder.get();
  }

  public final Song getSong2() {
    SongBuilder builder = SongBuilder.instance();
    builder = builder.withId("2").withTitle("Scandal of grace");
    builder = builder.withPart(SongPartType.VERS).withLine();
    builder = builder.withLinePart("Grace, what have you done? ", "G");
    builder = builder.withLine().withLinePart("Murdered for ", "Em");
    builder = builder.withLinePart("me on that ", "C");
    builder = builder.withLinePart("cross", "G");

    return builder.get();
  }

  public final List<Song> getExportTestData () {
    List<Song> songs = new ArrayList<Song>();
    songs.add(getSong1());
    songs.add(getSong2());

    return songs;

  }
}
