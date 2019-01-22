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
import org.adonai.screens.ScreenManager;
import org.adonai.ui.ExtensionSelectorController;
import org.adonai.ui.ExtensionType;
import org.adonai.ui.Mask;
import org.adonai.ui.MaskLoader;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class ConnectSongWithMp3Action {

  private static final Logger LOGGER = Logger.getLogger(OpenAudioAction.class.getName());

  public final static int CONNECTSONGDIALOG_WIDTH = 800;
  public final static int CONNECTSONGDIALOG_HEIGHT = 600;


  public void connect (final Double x, final Double y, Song selectedSong) {

    if (selectedSong == null)
      return;

    MaskLoader<ExtensionSelectorController> maskLoader = new MaskLoader<>();
    Mask<ExtensionSelectorController> mask = maskLoader.load("extensionselector");
    ExtensionSelectorController extensionSelectorController = mask.getController();
    extensionSelectorController.init(ExtensionType.SONG);
    Stage stage = mask.getStage();
    mask.setSize(CONNECTSONGDIALOG_WIDTH, CONNECTSONGDIALOG_HEIGHT);
    mask.setPosition(x, y);
    stage.setTitle("Connect song " + selectedSong.getTitle() + " with mp3 file");
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
