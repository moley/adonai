package org.adonai.export.presentation;

import java.util.Arrays;
import org.adonai.ApplicationEnvironment;
import org.adonai.SizeInfo;
import org.adonai.api.ExportBuilder;
import org.adonai.export.ExportConfiguration;
import org.adonai.export.ExportEngine;
import org.adonai.model.Model;
import org.adonai.model.Song;
import org.adonai.services.AddSongService;
import org.junit.Test;
import org.mockito.Mockito;
import org.pf4j.PluginManager;

public class PresentationExporterTest {


  @Test
  public void exportNewAddedSong () {

    PluginManager mockedPluginManager = Mockito.mock(PluginManager.class);
    Mockito.when(mockedPluginManager.getExtensions(ExportBuilder.class)).thenReturn(Arrays.asList(new PresentationDocumentBuilder()));

    AddSongService addSongService = new AddSongService();
    Model model = new Model();
    Song songToImport = addSongService.createSong("New song", true);
    ApplicationEnvironment applicationEnvironment = new ApplicationEnvironment(mockedPluginManager, model);


    PresentationExporter exporter = new PresentationExporter(applicationEnvironment, new SizeInfo(0,0), null);
    exporter.export(Arrays.asList(songToImport), null, new ExportConfiguration());

    applicationEnvironment.dispose();
  }
}
