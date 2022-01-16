package org.adonai.reader.text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartType;
import org.adonai.model.SongStructItem;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextfileReaderTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(TextfileReaderTest.class.getName());


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
  public void readIchWillDichAnbeten () throws IOException {
    List<String> content = FileUtils.readLines(new File("src/test/resources/import/text/IchWillDichAnbeten.txt"), "UTF-8");
    TextfileReaderParam param = new TextfileReaderParam();
    param.setEmptyLineIsNewPart(true);
    Song song = textfileReader.read(content, param);
    for (SongPart next: song.getSongParts()) {
      System.out.println(next.getSongPartType() + "-" + next.getEqualKey());
    }

    for (SongStructItem songStructItem : song.getStructItems()) {
      System.out.println(songStructItem.getPartId() + "-" + songStructItem.getText());
    }

    Assert.assertEquals (song.getSongParts().get(0).getId(), song.getStructItems().get(0).getPartId()); //VERS1
    Assert.assertEquals (song.getSongParts().get(1).getId(), song.getStructItems().get(1).getPartId()); //REFRAIN
    Assert.assertEquals (song.getSongParts().get(2).getId(), song.getStructItems().get(2).getPartId()); //VERS2
    Assert.assertEquals (song.getSongParts().get(1).getId(), song.getStructItems().get(3).getPartId()); //REFRAIN


  }

  @Test
  public void readWithRef2 () throws IOException {
    List<String> content = FileUtils.readLines(new File("src/test/resources/import/text/WithRef.txt"), "UTF-8");
    TextfileReaderParam param = new TextfileReaderParam();
    param.setEmptyLineIsNewPart(true);
    Song song = textfileReader.read(content, param);
    for (SongPart next: song.getSongParts()) {
      System.out.println(next.getSongPartType() + "-" + next.getEqualKey());
    }

    for (SongStructItem songStructItem : song.getStructItems()) {
      System.out.println(songStructItem.getPartId() + "-" + songStructItem.getText());
    }

    Assert.assertEquals (song.getSongParts().get(0).getId(), song.getStructItems().get(0).getPartId());
    Assert.assertEquals (song.getSongParts().get(1).getId(), song.getStructItems().get(1).getPartId());
    Assert.assertEquals (song.getSongParts().get(0).getId(), song.getStructItems().get(2).getPartId());

  }

  @Test
  public void readWithRef () throws IOException {
    List<String> content = FileUtils.readLines(new File("src/test/resources/import/text/WithRef.txt"), "UTF-8");
    TextfileReaderParam param = new TextfileReaderParam();
    param.setEmptyLineIsNewPart(true);
    Song song = textfileReader.read(content, param);
    for (SongPart next: song.getSongParts()) {
      System.out.println(next.getSongPartType() + "-" + next.getEqualKey());
    }

    for (SongStructItem songStructItem : song.getStructItems()) {
      System.out.println(songStructItem.getPartId() + "-" + songStructItem.getText());
    }

    Assert.assertEquals (song.getSongParts().get(0).getId(), song.getStructItems().get(0).getPartId());
    Assert.assertEquals (song.getSongParts().get(1).getId(), song.getStructItems().get(1).getPartId());
    Assert.assertEquals (song.getSongParts().get(0).getId(), song.getStructItems().get(2).getPartId());
    Assert.assertEquals (song.getSongParts().get(2).getId(), song.getStructItems().get(3).getPartId());

  }

  @Test
  public void readExampleWithoutPartTypes () throws IOException {
    List<String> content = FileUtils.readLines(new File("src/test/resources/import/text/SongWithoutPartTypes.txt"), "UTF-8");
    TextfileReaderParam param = new TextfileReaderParam();
    param.setEmptyLineIsNewPart(true);
    Song song = textfileReader.read(content, param);
    Assert.assertEquals(2, song.getSongParts().size());
    Assert.assertEquals ("First line in verse", song.getSongParts().get(0).getFirstLine().getText());
    Assert.assertEquals ("second line in verse", song.getSongParts().get(0).getLastLine().getText());
    Assert.assertEquals ("And then the refrain", song.getSongParts().get(1).getFirstLine().getText());
    Assert.assertEquals ("without any type", song.getSongParts().get(1).getLastLine().getText());

  }

  @Test
  public void readExample5() throws IOException {
    List<String> content = FileUtils.readLines(new File("src/test/resources/import/text/Ich tauche ein.txt"), "UTF-8");

    Song song = textfileReader.read(content, new TextfileReaderParam());
    SongPart intro = song.getFirstPart();
    SongStructItem songStructItem = song.getFirstStructItem();
    Assert.assertFalse ("2x must not be shown in line content", intro.getFirstLine().toString().contains("2x"));
    Assert.assertEquals ("2", songStructItem.getQuantity());
  }

  @Test
  public void readExample4() throws IOException {
    List<String> content = FileUtils.readLines(new File("src/test/resources/import/text/Was fuer ein Gott.txt"), "UTF-8");

    Song song = textfileReader.read(content, new TextfileReaderParam());
    SongPart refrain = song.getSongParts().get(2);
    Assert.assertTrue (refrain.getFirstLine().getText().startsWith("Jesus hier knie ich vor dir"));



  }

  @Test
  public void readExample3 () throws IOException {
    List<String> content = FileUtils.readLines(new File("src/test/resources/import/text/Ich weiss wer ich bin.txt"), "UTF-8");

    Song song = textfileReader.read(content, new TextfileReaderParam());
    //[Intro]     *
    //[Verse 1]   *
    //[Chorus 1]  *
    //[Verse 2]   *
    //[Chorus 2]  *
    //[Bridge 1]  *
    //[Bridge 2]  *
    //[Chorus 3]  *
    //[Interlude]  *
    //[Bridge 1]
    //[Bridge 1]
    //[Bridge 1]
    //[Bridge 3]   *
    //[Chorus 2]
    Assert.assertEquals (10, song.getSongParts().size()); //all with * are new parts
    SongPart introPart = song.getSongParts().get(0);
    SongPart vers1Part = song.getSongParts().get(1);
    SongPart chorus1Part = song.getSongParts().get(2);
    SongPart vers2Part = song.getSongParts().get(3);
    SongPart chorus2Part = song.getSongParts().get(4);
    SongPart bridge1Part = song.getSongParts().get(5);
    SongPart bridge2Part = song.getSongParts().get(6);
    SongPart chorus3Part = song.getSongParts().get(7);
    SongPart interludePart = song.getSongParts().get(8);
    SongPart bridge3Part = song.getSongParts().get(9);

    Assert.assertEquals (14, song.getStructItems().size()); //all with * are new parts
    SongStructItem chorus2PartStruct = song.getStructItems().get(4);
    SongStructItem chorus2RefStruct = song.getStructItems().get(13);

    Assert.assertEquals (chorus2Part, song.findSongPart(chorus2PartStruct));
    Assert.assertEquals (chorus2Part, song.findSongPart(chorus2RefStruct));

    Assert.assertEquals (SongPartType.INTRO, introPart.getSongPartType());
    Assert.assertEquals (SongPartType.VERS, vers1Part.getSongPartType());
    Assert.assertEquals (SongPartType.REFRAIN, chorus1Part.getSongPartType());
    Assert.assertEquals (SongPartType.VERS, vers2Part.getSongPartType());
    Assert.assertEquals (SongPartType.REFRAIN, chorus2Part.getSongPartType());
    Assert.assertEquals (SongPartType.BRIDGE, bridge1Part.getSongPartType());
    Assert.assertEquals (SongPartType.BRIDGE, bridge2Part.getSongPartType());
    Assert.assertEquals (SongPartType.REFRAIN, chorus3Part.getSongPartType());
    Assert.assertEquals (SongPartType.ZWISCHENSPIEL, interludePart.getSongPartType());
    Assert.assertEquals (SongPartType.BRIDGE, bridge3Part.getSongPartType());
  }

  @Test
  public void readExample2 () throws IOException {
    List<String> content = FileUtils.readLines(new File("src/test/resources/import/text/Ich hab noch nie.txt"), "UTF-8");

    Song song = textfileReader.read(content, new TextfileReaderParam());

  }

  @Test
  public void readExample () throws IOException {
    List<String> content = FileUtils.readLines(new File("src/test/resources/import/text/Das Glaube ich.txt"), "UTF-8");

    Song song = textfileReader.read(content, new TextfileReaderParam());
  }

  @Test
  public void quantityInChordline () {
    List<String> allLines = Arrays.asList("[Verse 1]", "Am       F (2x)", "   Alles   ist cool");
    Song song = textfileReader.read(allLines, new TextfileReaderParam());
    SongPart firstPart = song.getFirstPart();
    SongStructItem firstStructItem = song.getFirstStructItem();
    Assert.assertFalse ("2x must not be shown in line content", firstPart.getFirstLine().toString().contains("2x"));
    Assert.assertEquals ("2", firstStructItem.getQuantity());

  }

  @Test
  public void quantityInTextline () {
    List<String> allLines = Arrays.asList("[Verse 1]", "Am       F", "   Alles   ist cool  (2x)");
    Song song = textfileReader.read(allLines, new TextfileReaderParam());
    SongStructItem firstStructItem = song.getFirstStructItem();
    SongPart firstPart = song.getFirstPart();
    Assert.assertFalse ("2x must not be shown in line content", firstPart.getFirstLine().toString().contains("2x"));
    Assert.assertEquals ("2", firstStructItem.getQuantity());

  }

  @Test
  public void doNotStripInMiddle () {
    List<String> allLines = Arrays.asList("[Verse 1]", "Am       F", "   Alles   ist cool");
    SongPart firstPart = textfileReader.read(allLines, new TextfileReaderParam()).getFirstPart();
    Assert.assertEquals ("Alles ", firstPart.getFirstLine().getLineParts().get(0).getText());
    Assert.assertEquals ("Am", firstPart.getFirstLine().getLineParts().get(0).getChord());
    Assert.assertEquals ("  ist cool", firstPart.getFirstLine().getLineParts().get(1).getText());
    Assert.assertEquals ("F", firstPart.getFirstLine().getLineParts().get(1).getChord());

  }

  @Test
  public void stripBeginning () {
    List<String> allLines = Arrays.asList("[Verse 1]", "Am       F", "   Alles ist cool");
    SongPart firstPart = textfileReader.read(allLines, new TextfileReaderParam()).getFirstPart();
    Assert.assertEquals ("Alles ", firstPart.getFirstLine().getLineParts().get(0).getText());
    Assert.assertEquals ("Am", firstPart.getFirstLine().getLineParts().get(0).getChord());
    Assert.assertEquals ("ist cool", firstPart.getFirstLine().getLineParts().get(1).getText());
    Assert.assertEquals ("F", firstPart.getFirstLine().getLineParts().get(1).getChord());

  }

  @Test
  public void determiningTypes () {
    List<String> allLines = Arrays.asList("[Verse 1]", "Hello", "", "[Pre-Chorus]", "Hello", "");
    Song song = textfileReader.read(allLines, new TextfileReaderParam());
    Assert.assertEquals ("First part has wrong type", SongPartType.VERS, song.getSongParts().get(0).getSongPartType());
    Assert.assertEquals ("Second part has wrong type", SongPartType.BRIDGE, song.getSongParts().get(1).getSongPartType());
  }

  @Test
  public void duplicateParts () {
    List<String> allLines = Arrays.asList("[Verse 1]", "Vers1Zeile1", "", "[Chorus]", "Chorus", "[Verse 1]", "Vers1Zeile1");
    LOGGER.info(allLines.toString().replace(",", "\n"));
    Song song = textfileReader.read(allLines, new TextfileReaderParam());
    Assert.assertEquals (2, song.getSongParts().size());
    SongPart firstPart = song.getSongParts().get(0);
    SongPart secondPart = song.getSongParts().get(1);
    Assert.assertEquals (3, song.getStructItems().size());
    SongStructItem firstStructItem = song.getStructItems().get(0);
    SongStructItem secondStructItem = song.getStructItems().get(1);
    SongStructItem thirdStructItem = song.getStructItems().get(2);
    Assert.assertEquals ("First part has wrong type", SongPartType.REFRAIN, secondPart.getSongPartType());
    Assert.assertEquals ("PartID invalid", firstPart.getId(), firstStructItem.getPartId());
    Assert.assertEquals ("PartID invalid", secondPart.getId(), secondStructItem.getPartId());
    Assert.assertEquals ("PartID invalid", firstPart.getId(), thirdStructItem.getPartId());
    LOGGER.info("StructItems: " + song.getStructItems());
    LOGGER.info("Parts: " + song.getSongParts());
  }


  @Test
  public void isChordLineValid () {
    Assert.assertTrue (textfileReader.isChordLine("G D/F# A F#m g# Dsus4"));
    Assert.assertTrue (textfileReader.isChordLine("F                 Am    G                  C/E  F      G      C/E"));

  }

  @Test
  public void twoChordLines () {
    Song imported = textfileReader.read(createLine("A G/H C","C H Am"), new TextfileReaderParam());
    Line firstLine = imported.getFirstPart().getLines().get(0);
    Line secondLine = imported.getFirstPart().getLines().get(1);

    Assert.assertEquals ("A", firstLine.getFirstLinePart().getChord());
    Assert.assertEquals ("C", secondLine.getFirstLinePart().getChord());


  }

  @Test
  public void twoTextLines () {
    Song imported = textfileReader.read(createLine("First line","second line"), new TextfileReaderParam());
    Assert.assertEquals ("First line", imported.getFirstPart().getLines().get(0).getFirstLinePart().getText());
    Assert.assertNull(imported.getFirstPart().getLines().get(0).getFirstLinePart().getChord());
    Assert.assertEquals ("second line", imported.getFirstPart().getLines().get(1).getFirstLinePart().getText());
    Assert.assertNull(imported.getFirstPart().getLines().get(0).getLineParts().get(0).getChord());
    Assert.assertEquals (2, imported.getFirstPart().getLines().size());

  }

  @Test
  public void isChordLineInvalid () {
    Assert.assertFalse (textfileReader.isChordLine("G D/F# A F#m g# Dsus4 das ist klar"));
  }

  @Test
  public void withoutText () {
    Song imported = textfileReader.read(createLine("A G/H C",null), new TextfileReaderParam());
    Line line = imported.getFirstPart().getFirstLine();
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
    Song imported = textfileReader.read(createLine("Dies ist ein Test",null), new TextfileReaderParam());
    Line line = imported.getFirstPart().getFirstLine();
    LinePart linePart = line.getLineParts().get(0);
    Assert.assertEquals ("Dies ist ein Test", linePart.getText());
    Assert.assertEquals (1, line.getLineParts().size());


  }

  @Test
  public void chordLongerThanText () {
    Song imported = textfileReader.read(createLine(      "F                          G  a  G/H",
      "Und an Christus, Seinen Sohn"),new TextfileReaderParam());

    //F                          G  a  G/H
    //Und an Christus, Seinen Sohn
    Line line = imported.getFirstPart().getFirstLine();
    LinePart linePart1 =  line.getLineParts().get(0);
    LinePart linePart2 =  line.getLineParts().get(1);
    LinePart linePart3 =  line.getLineParts().get(2);
    LinePart linePart4 =  line.getLineParts().get(3);
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
      "Und an Christus, Seinen Sohn"), new TextfileReaderParam());

    //F
    //Und an Christus, Seinen Sohn
    Line line = imported.getFirstPart().getFirstLine();
    LinePart linePart1 =  line.getLineParts().get(0);
    LinePart linePart2 =  line.getLineParts().get(1);

    Assert.assertEquals ("F", linePart1.getChord());
    Assert.assertEquals ("Und an Christus, Seinen Soh", linePart1.getText());

    Assert.assertEquals ("G", linePart2.getChord());
    Assert.assertEquals ("n", linePart2.getText());
  }

  @Test
  public void chordStartsLater () {
    Song imported = textfileReader.read(createLine("       F                G",
                                                                "Und an Christus, Seinen Sohn"), new TextfileReaderParam());

    //Und an Christus, Seinen Sohn
    Line line = imported.getFirstPart().getFirstLine();
    LinePart linePart1 =  line.getLineParts().get(0);
    LinePart linePart2 =  line.getLineParts().get(1);
    LinePart linePart3 =  line.getLineParts().get(2);

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
