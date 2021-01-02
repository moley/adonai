package org.adonai.actions;

import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.adonai.ApplicationEnvironment;
import org.adonai.fx.ExtensionSelectorController;
import org.adonai.fx.ExtensionType;
import org.adonai.fx.Mask;
import org.adonai.fx.MaskLoader;
import org.adonai.fx.ScreenManager;
import org.adonai.model.Configuration;
import org.adonai.model.Song;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectSongWithMp3Action {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConnectSongWithMp3Action.class);

  private ScreenManager screenManager = new ScreenManager();

  public ExtensionSelectorController getExtensionSelectorController() {
    return extensionSelectorController;
  }

  private ExtensionSelectorController extensionSelectorController;


  public void connect (ApplicationEnvironment applicationEnvironment, Configuration configuration, Song selectedSong, EventHandler<WindowEvent> onHiding) {

    if (selectedSong == null) {
      LOGGER.warn("Selected song is <null>");
      return;
    }


    MaskLoader<ExtensionSelectorController> maskLoader = new MaskLoader<>();
    Mask<ExtensionSelectorController> mask = maskLoader.load( "extensionselector");
    extensionSelectorController = mask.getController();
    extensionSelectorController.init(ExtensionType.SONG, configuration);
    Stage stage = mask.getStage();
    screenManager.layoutOnScreen(stage, 100, applicationEnvironment.getAdminScreen());
    stage.setTitle("Connect song " + selectedSong.getTitle() + " with mp3 file");
    stage.setOnHiding(onHiding);

    stage.show();
  }
}
