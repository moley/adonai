package org.adonai.fx.imports.pages;

import java.util.Arrays;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.adonai.ApplicationEnvironment;
import org.adonai.SizeInfo;
import org.adonai.export.ExportConfiguration;
import org.adonai.export.presentation.PresentationDocumentBuilder;
import org.adonai.export.presentation.PresentationExporter;
import org.adonai.fx.SongViewer;
import org.adonai.fx.editcontent.KeyType;
import org.adonai.fx.imports.SongImportController;
import org.adonai.model.Configuration;
import org.adonai.model.Song;
import org.controlsfx.control.Notifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PreviewPage extends WizardPage {

  private static final Logger LOGGER = LoggerFactory.getLogger(PreviewPage.class);

  private Configuration configuration;

  VBox rootpanel;

  public final static String TITLE = "Preview";
  public PreviewPage(ApplicationEnvironment applicationEnvironment, final Stage stage, SongImportController controller, Configuration configuration) {
    super(applicationEnvironment, stage, TITLE, controller);
    this.configuration = configuration;
  }

  public void fillContent () {

    Song song = controller.getSongToImport();
    LOGGER.info("Song: " + song.getSongParts());
    List<Song> songsOfCurrentScope = Arrays.asList(song);

    SizeInfo sizeInfo = new SizeInfo(rootpanel.getScene().getWidth() - 100, rootpanel.getScene().getHeight() - 150);
    try {

      PresentationExporter exporter = new PresentationExporter(applicationEnvironment, sizeInfo, new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent event) {
          LOGGER.info("Hello");
        }
      });

      Configuration configuration = applicationEnvironment.getCurrentConfiguration();
      ExportConfiguration exportConfiguration = configuration.findDefaultExportConfiguration(PresentationDocumentBuilder.class);

      exportConfiguration.setKeyType(KeyType.CURRENT);
      exportConfiguration.setWithHiddenTitles(true);
      exportConfiguration.setWithTitle(false);
      exportConfiguration.setWithLead(false);
      exportConfiguration.setWithId(false);
      exportConfiguration.setWithKeys(false);
      exportConfiguration.setWithLead(false);
      exportConfiguration.setWithKeys(false);
      exportConfiguration.setWithChords(true);

      exporter.export(songsOfCurrentScope, null, exportConfiguration);
      SongViewer songEditor = new SongViewer(applicationEnvironment, exporter.getPanes());
      rootpanel.getChildren().clear();
      rootpanel.getChildren().add(songEditor);
      songEditor.show();
    } catch (Exception e) {
      LOGGER.info(e.getLocalizedMessage(), e);
      Notifications.create().text("Error on preview: " + e.getLocalizedMessage()).showError();
    }
  }

  Parent getContent() {
    rootpanel = new VBox();


    rootpanel.setUserData("importsongwizard.rootpanel");

    VBox.setVgrow(rootpanel, Priority.ALWAYS);

    rootpanel.sceneProperty().addListener(new ChangeListener<Scene>() {
      @Override
      public void changed(ObservableValue<? extends Scene> observable, Scene oldValue, Scene newValue) {
        if (LOGGER.isDebugEnabled())
          LOGGER.debug("Visibility change: " + oldValue + "->" + newValue);
        if (oldValue == null && newValue != null) {
          LOGGER.info("Preview for " + controller.getSongToImport().toString() );

          fillContent();


        }
      }
    });
    return rootpanel;
  }
}
