package org.adonai.actions;

import java.io.File;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.adonai.actions.openAdditionals.OpenAudioAction;
import org.adonai.additionals.AdditionalsImporter;
import org.adonai.model.Additional;
import org.adonai.model.AdditionalType;
import org.adonai.model.Song;
import org.adonai.ui.ExtensionSelectorController;
import org.adonai.ui.ExtensionType;
import org.adonai.ui.Mask;
import org.adonai.ui.MaskLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectSongWithMp3Action {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConnectSongWithMp3Action.class);

  public final static int CONNECTSONGDIALOG_WIDTH = 800;
  public final static int CONNECTSONGDIALOG_HEIGHT = 600;


  public void connect (final Double x, final Double y, Song selectedSong) {

    if (selectedSong == null) {
      LOGGER.warn("Selected song is <null>");
      return;
    }

    MaskLoader<ExtensionSelectorController> maskLoader = new MaskLoader<>();
    Mask<ExtensionSelectorController> mask = maskLoader.load("extensionselector");
    ExtensionSelectorController extensionSelectorController = mask.getController();
    extensionSelectorController.init(ExtensionType.SONG);
    Stage stage = mask.getStage();
    mask.setSize(CONNECTSONGDIALOG_WIDTH, CONNECTSONGDIALOG_HEIGHT);
    mask.setPosition(x, y);
    stage.setTitle("Connect song " + selectedSong.getTitle() + " with mp3 file");
    stage.setOnHiding(new EventHandler<WindowEvent>() {

      @Override
      public void handle(WindowEvent event) {
        LOGGER.info("handle onHiding event");

        File selectedExtension = extensionSelectorController.getSelectedExtension();

        if (selectedExtension != null) {
          String songExtension = selectedExtension.getAbsolutePath();
          Additional additional = new Additional();
          additional.setAdditionalType(AdditionalType.AUDIO);
          additional.setLink(songExtension);
          AdditionalsImporter additionalsImporter = new AdditionalsImporter();
          additionalsImporter.refreshCache(selectedSong, additional, true);
          selectedSong.setAdditional(additional);
          LOGGER.info("connect song " + selectedSong + " with songfile " + selectedExtension);
        }
        else
          LOGGER.info("no extension selected");
      }
    });

    stage.show();
  }
}
