package org.adonai.importer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import org.adonai.Chord;
import org.adonai.InvalidChordException;
import org.adonai.model.Configuration;
import org.adonai.model.Song;
import org.adonai.model.SongBook;
import org.adonai.model.SongPartType;
import org.adonai.model.Status;
import org.adonai.reader.text.TextfileReader;
import org.adonai.reader.text.TextfileReaderParam;
import org.adonai.services.AddSongService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WordImporter {

  private static final Logger LOGGER = LoggerFactory.getLogger(WordImporter.class);


  private AddSongService addSongService = new AddSongService();


  private boolean isChordsLine(final String line) {
    String[] tokens = line.split("\\s+");
    for (String next : tokens) {
      if (next.trim().isEmpty())
        continue;

      try {
        Chord chord = new Chord(next.trim());
      } catch (InvalidChordException e) {
        return false;
      }
    }

    return true;

  }

  public Collection<Song> importFromWordFile(final Configuration configuration, final File file) throws InvalidFormatException, IOException {

    XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(file));
    XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
    String wordFileContent = extractor.getText();

    WordImporterItem currentItem = null;
    Collection<WordImporterItem> importerItems = new ArrayList<>();

    for (String nextLine : wordFileContent.split("\n")) {
      if (nextLine.trim().isEmpty()) {
        if (!currentItem.getContent().get(currentItem.getContent().size() - 1).trim().isEmpty())
          currentItem.getContent().add("");
      } else {
        String[] tokens = nextLine.split("\t");
        if (tokens.length > 0) {
          try {
            Integer songnumber = Integer.valueOf(tokens[0]);
            String name = nextLine.replace(songnumber.toString(), "").trim();

            currentItem = new WordImporterItem();
            currentItem.setName(name);
            currentItem.setNumber(songnumber);

            importerItems.add(currentItem);
            currentItem.getContent().add(name);
            currentItem.getContent().add("");

          } catch (NumberFormatException e) {
            if (!isChordsLine(nextLine)) {
              for (SongPartType nextType : SongPartType.values()) {
                if (nextLine.startsWith(nextType.name())) {
                  currentItem.getContent().add("");
                  currentItem.getContent().add("[" + nextType.name() + "]");
                  nextLine = nextLine.replace(nextType.name(), "");
                  break;
                }

              }
              currentItem.getContent().add(nextLine.trim());
            }
          }

        }
      }


    }

    Integer nextNumber = addSongService.getNextNumber(configuration.getSongBooks().get(0));

    for (WordImporterItem nextItem : importerItems) {

      LOGGER.info(String.join("\n", nextItem.getContent()));
      LOGGER.info("------");

      TextfileReader textfileReader = new TextfileReader();
      TextfileReaderParam param = new TextfileReaderParam();
      param.setEmptyLineIsNewPart(true);

      Song song = textfileReader.read(nextItem.getContent(), param);
      nextItem.setSong(song);
      nextItem.getSong().setId(nextNumber);
      nextItem.getSong().setTitle(nextItem.getName());
      nextItem.getSong().setStatus(Status.NEW);

      for (SongBook nextSongbook : configuration.getSongBooks()) {
        if (nextSongbook.findSongByName(nextItem.getName()) != null)
          nextItem.setExists(true);
      }

      nextNumber = nextNumber.intValue() + 1;

    }

    Collection<Song> songs = new ArrayList<Song>();
    for (WordImporterItem nextItem : importerItems) {
      if (!nextItem.isExists()) {
        songs.add(nextItem.getSong());
      }
    }

    return songs;

  }
}
