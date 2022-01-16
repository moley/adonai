package org.adonai;

import org.adonai.model.Song;
import org.adonai.model.SongBuilder;
import org.adonai.model.SongPartType;

import java.util.ArrayList;
import java.util.Collection;

public class SongTestData {

  public final static Song getSongWithTwoPartsAndOneReference() {
    SongBuilder builder = SongBuilder.instance();
    builder = builder.withId("1").withTitle("Example 1");
    builder = builder.withPart(SongPartType.VERS).withPartId("1").withLine();
    builder = builder.withLinePart("This is ", "D");
    builder = builder.withLinePart("the first testline", "G");
    builder = builder.withLine().withLinePart("This is the second", "A");

    builder = builder.withPart(SongPartType.REFRAIN).withLine();
    builder = builder.withLinePart("And a second part", "D");
    builder = builder.withLinePart("with a second line", "G");
    builder = builder.withLine().withLinePart("This is the second of second part", "A");

    builder = builder.withPartReference("1");
    return builder.get();
  }

  public final static Song getSongWithTwoPartsTwoLines() {
    SongBuilder builder = SongBuilder.instance();
    builder = builder.withId("1").withTitle("Example 1");
    builder = builder.withPart(SongPartType.VERS).withLine();
    builder = builder.withLinePart("This is", "D");
    builder = builder.withLinePart("the first testline", "G");
    builder = builder.withLine().withLinePart("This is the second", "A");

    builder = builder.withPart(SongPartType.REFRAIN).withLine();
    builder = builder.withLinePart("And a second part", "D");
    builder = builder.withLinePart("with a second line", "G");
    builder = builder.withLine().withLinePart("This is the second of second part", "A");
    return builder.get();
  }

  public final static Song getSongWithTwoVerses() {
    SongBuilder builder = SongBuilder.instance();
    builder = builder.withId("1").withTitle("Example 1");
    builder = builder.withPart(SongPartType.VERS).withPartId("1").withLine();
    builder = builder.withLinePart("This is", "D");
    builder = builder.withLinePart("the first testline", "G");
    builder = builder.withLine().withLinePart("This is the second", "A");

    builder = builder.withPart(SongPartType.VERS).withPartId("2").withLine();
    builder = builder.withLinePart("And a second part", "D");
    builder = builder.withLinePart("with a second line", "G");
    builder = builder.withLine().withLinePart("This is the second of second part", "A");

    builder = builder.withPartReference("1");
    builder = builder.withPartReference("2");
    return builder.get();
  }


  public final static Song getSongWithTwoParts() {
    SongBuilder builder = SongBuilder.instance();
    builder = builder.withId("2").withTitle("Example 2");
    builder = builder.withPart(SongPartType.VERS);
    builder = builder.withLine().withLinePart("This is ", "D");
    builder = builder.withLinePart("the first testline", "G");
    builder = builder.withLine().withLinePart("This is ", "D");
    builder = builder.withLinePart("the second testline", "G");

    builder = builder.withPart(SongPartType.REFRAIN).withLine();
    builder = builder.withLinePart("And a second part", "D");
    builder = builder.withLinePart("with a second line", "G");
    return builder.get();
  }

  public final static Song getSongWithOnePart() {
    SongBuilder builder = SongBuilder.instance();
    builder = builder.withId("3").withTitle("Example 3");
    builder = builder.withPart(SongPartType.VERS).withLine();
    builder = builder.withLinePart("That is", "D");
    builder = builder.withLinePart("one testline", "G");
    return builder.get();
  }

  public final static Collection<Song> getSongBook () {
    Collection<Song> songs = new ArrayList<Song>();
    songs.add(getSongWithTwoPartsTwoLines());
    songs.add(getSongWithTwoParts());
    songs.add(getSongWithOnePart());

    return songs;
  }
}
