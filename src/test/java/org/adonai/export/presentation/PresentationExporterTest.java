package org.adonai.export.presentation;

import java.util.Arrays;
import org.adonai.ApplicationEnvironment;
import org.adonai.SizeInfo;
import org.adonai.export.ExportConfiguration;
import org.adonai.model.Model;
import org.adonai.model.Song;
import org.adonai.services.AddSongService;
import org.junit.Test;

public class PresentationExporterTest {


  @Test
  public void exportNewAddedSong () {

    AddSongService addSongService = new AddSongService();
    Model model = new Model();
    Song songToImport = addSongService.createSong("New song", true);
    ApplicationEnvironment applicationEnvironment = new ApplicationEnvironment(model);


    PresentationExporter exporter = new PresentationExporter(applicationEnvironment, new SizeInfo(0,0), null);
    exporter.export(Arrays.asList(songToImport), null, new ExportConfiguration());

    applicationEnvironment.dispose();
  }
}
