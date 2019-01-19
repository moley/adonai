package org.adonai.actions;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.adonai.actions.openAdditionals.OpenAudioAction;
import org.adonai.additionals.AdditionalsImporter;
import org.adonai.model.Additional;
import org.adonai.model.AdditionalType;
import org.adonai.model.Song;
import org.adonai.ui.ExtensionSelectorController;
import org.adonai.ui.ExtensionType;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class ConnectSongWithMp3Action {

  private static final Logger LOGGER = Logger.getLogger(OpenAudioAction.class.getName());


  public void connect (Song selectedSong) {

    if (selectedSong == null)
      return;

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens/extensionselector.fxml"));
    Parent root = null;
    try {
      root = loader.load();
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
    ExtensionSelectorController extensionSelectorController = loader.getController();
    extensionSelectorController.init(ExtensionType.SONG);

    Scene scene = new Scene(root, 800, 600);
    scene.getStylesheets().add("/adonai.css");

    Stage stage = new Stage();
    stage.setTitle("Connect song " + selectedSong.getTitle() + " with mp3 file");
    stage.setScene(scene);
    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

      @Override
      public void handle(WindowEvent event) {

        if (selectedSong != null && extensionSelectorController.getSelectedExtension() != null) {
          String songExtension = extensionSelectorController.getSelectedExtension().getAbsolutePath();
          Additional additional = new Additional();
          additional.setAdditionalType(AdditionalType.AUDIO);
          additional.setLink(songExtension);
          AdditionalsImporter additionalsImporter = new AdditionalsImporter();
          try {
            File additionalFile = additionalsImporter.getAdditional(additional);
          } catch (IOException e) {
            throw new IllegalStateException(e);
          }
          selectedSong.setAdditional(additional);
          LOGGER.info("connect song " + selectedSong + " with songfile " + extensionSelectorController.getSelectedExtension());

        }
      }
    });

    stage.show();
  }
}
