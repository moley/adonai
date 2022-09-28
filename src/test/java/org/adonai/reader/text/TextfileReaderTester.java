package org.adonai.reader.text;

import com.glaforge.i18n.io.CharsetToolkit;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartType;
import org.adonai.model.SongStructItem;
import org.adonai.reader.chordpro.ChordProFileReader;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextfileReaderTester {

  public static void main(String[] args) throws IOException {
    TextfileReader chordProFileReader = new TextfileReader();
    File textfile = new File("bla.txt"); //TODO change

    Charset cs = CharsetToolkit.guessEncoding(textfile, 4096, StandardCharsets.UTF_8);

    List<String> lines = FileUtils.readLines(textfile, cs);
    TextfileReaderParam textfileReaderParam = new TextfileReaderParam();
    textfileReaderParam.setEmptyLineIsNewPart(true);
    textfileReaderParam.setWithTitle(true);
    Song song = chordProFileReader.read(lines, textfileReaderParam);

    System.out.println (song.toString());



  }
}
