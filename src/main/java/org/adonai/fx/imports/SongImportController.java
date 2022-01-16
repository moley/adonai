package org.adonai.fx.imports;

import org.adonai.model.Song;
import org.adonai.model.SongBook;
import org.adonai.services.AddSongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SongImportController {

  private static final Logger LOGGER = LoggerFactory.getLogger(SongImportController.class);


  private SongBook songBook;

  Song songToImport;


  public SongImportController () {
    AddSongService addSongService = new AddSongService();
    songToImport = addSongService.createSong("New song", true);
  }

  public Song getSongToImport() {
    return songToImport;
  }

  public void setSongToImport(Song songToImport) {
    if (songToImport != null)
      LOGGER.info("set song to import: " + songToImport.getTitle() + "-" + songToImport.getStructItems().size());
    else
      LOGGER.info("set song null");

    this.songToImport = songToImport;
  }

  public SongBook getSongBook() {
    return songBook;
  }

  public void setSongBook(SongBook songBook) {
    this.songBook = songBook;
  }




}
