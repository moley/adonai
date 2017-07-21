package org.adonai.reader.text;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.model.Song;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextfileReaderTest {

  TextfileReader textfileReader = new TextfileReader();


  private List<String> createLine (final String chord,
                             final String text) {

    List<String> lines = new ArrayList<String>();
    lines.add("Title");
    lines.add("");
    lines.add("[Vers]");
    if (chord != null)
      lines.add(chord);

    if (text != null)
      lines.add(text);
    return lines;
  }

  @Test
  public void readExample () throws IOException {
    List<String> content = FileUtils.readLines(new File("src/test/resources/import/text/Das Glaube ich.txt"), "UTF-8");

    Song song = textfileReader.read(content);
    System.out.println ("Song: " + song.toString());

  }



  @Test
  public void isChordLineValid () {
    Assert.assertTrue (textfileReader.isChordLine("G D/F# A F#m g# Dsus4"));
    Assert.assertTrue (textfileReader.isChordLine("F                 Am    G                  C/E  F      G      C/E"));

  }

  @Test
  public void twoChordLines () {
    Song imported = textfileReader.read(createLine("A G/H C","C H Am"));
    Line firstLine = imported.getFirstSongPart().getLines().get(0);
    Line secondLine = imported.getFirstSongPart().getLines().get(1);

    Assert.assertEquals ("A", firstLine.getFirstLinePart().getChord());
    Assert.assertEquals ("C", secondLine.getFirstLinePart().getChord());


  }

  @Test
  public void twoTextLines () {
    Song imported = textfileReader.read(createLine("First line","second line"));
    System.out.println (imported);
    Assert.assertEquals ("First line", imported.getFirstSongPart().getLines().get(0).getFirstLinePart().getText());
    Assert.assertNull(imported.getFirstSongPart().getLines().get(0).getFirstLinePart().getChord());
    Assert.assertEquals ("second line", imported.getFirstSongPart().getLines().get(1).getFirstLinePart().getText());
    Assert.assertNull(imported.getFirstSongPart().getLines().get(0).getLineParts().get(0).getChord());
    Assert.assertEquals (2, imported.getFirstSongPart().getLines().size());

  }

  @Test
  public void isChordLineInvalid () {
    Assert.assertFalse (textfileReader.isChordLine("G D/F# A F#m g# Dsus4 das ist klar"));
  }

  @Test
  public void withoutText () {
    Song imported = textfileReader.read(createLine("A G/H C",null));
    System.out.println (imported);
    Line line = imported.getFirstSongPart().getFirstLine();
    LinePart linePart1 =  line.getLineParts().get(0);
    Assert.assertEquals ("A", linePart1.getChord());
    LinePart linePart2 =  line.getLineParts().get(1);
    Assert.assertEquals ("G/H", linePart2.getChord());
    LinePart linePart3 =  line.getLineParts().get(2);
    Assert.assertEquals ("C", linePart3.getChord());
    Assert.assertEquals (3, line.getLineParts().size());



  }

  @Test
  public void withoutChords () {
    Song imported = textfileReader.read(createLine("Dies ist ein Test",null));
    System.out.println (imported);
    Line line = imported.getFirstSongPart().getFirstLine();
    LinePart linePart = line.getLineParts().get(0);
    Assert.assertEquals ("Dies ist ein Test", linePart.getText());
    Assert.assertEquals (1, line.getLineParts().size());


  }

  @Test
  public void chordLongerThanText () {
    Song imported = textfileReader.read(createLine(      "F                          G  a  G/H",
      "Und an Christus, Seinen Sohn"));

    //F                          G  a  G/H
    //Und an Christus, Seinen Sohn
    System.out.println (imported);
    Line line = imported.getFirstSongPart().getFirstLine();
    LinePart linePart1 =  line.getLineParts().get(0);
    LinePart linePart2 =  line.getLineParts().get(1);
    LinePart linePart3 =  line.getLineParts().get(2);
    LinePart linePart4 =  line.getLineParts().get(3);

    System.out.println (line);

    Assert.assertEquals ("F", linePart1.getChord());
    Assert.assertEquals ("Und an Christus, Seinen Soh", linePart1.getText());

    Assert.assertEquals ("G", linePart2.getChord());
    Assert.assertEquals ("n", linePart2.getText());

    Assert.assertEquals ("Am", linePart3.getChord());
    Assert.assertEquals (" ", linePart3.getText());

    Assert.assertEquals ("G/H", linePart4.getChord());
    Assert.assertEquals (" ", linePart4.getText());
  }


  @Test
  public void textLongerThanChord () {
    Song imported = textfileReader.read(createLine(      "F                          G",
      "Und an Christus, Seinen Sohn"));

    //F
    //Und an Christus, Seinen Sohn
    System.out.println (imported);
    Line line = imported.getFirstSongPart().getFirstLine();
    LinePart linePart1 =  line.getLineParts().get(0);
    LinePart linePart2 =  line.getLineParts().get(1);

    System.out.println (line);

    Assert.assertEquals ("F", linePart1.getChord());
    Assert.assertEquals ("Und an Christus, Seinen Soh", linePart1.getText());

    Assert.assertEquals ("G", linePart2.getChord());
    Assert.assertEquals ("n", linePart2.getText());
  }

  @Test
  public void chordStartsLater () {
    Song imported = textfileReader.read(createLine("       F                G",
                                                                "Und an Christus, Seinen Sohn"));

    //Und an Christus, Seinen Sohn
    System.out.println (imported);
    Line line = imported.getFirstSongPart().getFirstLine();
    LinePart linePart1 =  line.getLineParts().get(0);
    LinePart linePart2 =  line.getLineParts().get(1);
    LinePart linePart3 =  line.getLineParts().get(2);

    System.out.println (line);

    Assert.assertNull (linePart1.getChord());
    Assert.assertEquals ("Und an ", linePart1.getText());

    Assert.assertEquals ("F", linePart2.getChord());
    Assert.assertEquals ("Christus, Seinen ", linePart2.getText());

    Assert.assertEquals ("G", linePart3.getChord());
    Assert.assertEquals ("Sohn", linePart3.getText());

  }

  @Test
  public void getTokens () {
    String line = "G           D/F#         A         F#m       g#      Dsus4";
    List<Integer> tokens = textfileReader.getTokens(line);
    for (Integer next: tokens) {
      Assert.assertFalse (Character.isWhitespace(line.charAt(next)));
    }
    Assert.assertEquals ("Number of tokens invalid", 6, tokens.size());
    Assert.assertEquals ('D', line.charAt(tokens.get(1)));
    Assert.assertEquals ('A', line.charAt(tokens.get(2)));
    Assert.assertEquals ('F', line.charAt(tokens.get(3)));
  }
}
