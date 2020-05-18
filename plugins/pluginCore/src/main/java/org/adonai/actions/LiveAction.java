package org.adonai.actions;

import java.util.Collection;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.adonai.SizeInfo;
import org.adonai.export.ExportConfiguration;
import org.adonai.export.ExportToken;
import org.adonai.export.presentation.PresentationDocumentBuilder;
import org.adonai.export.presentation.PresentationExporter;
import org.adonai.model.Configuration;
import org.adonai.model.Song;
import org.adonai.presentation.ControlView;
import org.adonai.ui.Consts;
import org.adonai.ui.ScreenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LiveAction {

  private static final Logger LOGGER = LoggerFactory.getLogger(LiveAction.class);


  public void startLiveSession (Configuration configuration, Collection<Song> songs, String name) {

    SizeInfo sizeInfo = new SizeInfo(Consts.getDefaultWidth(), Consts.getDefaultHeight());
    PresentationExporter exporter = new PresentationExporter(null, sizeInfo, null);

    ExportConfiguration exportConfiguration = configuration.findDefaultExportConfiguration(PresentationDocumentBuilder.class);

    exporter.export(songs, null, exportConfiguration);


    for (ExportToken next: exporter.getExportTokenContainer().getExportTokenList()) {
      LOGGER.info("Next: " + next.getText() + " - " + next.getAreaInfo() + " - " + next.getExportTokenType());
    }

    ControlView root = new ControlView(exporter.getPanes());
    Scene scene = new Scene(root, Consts.getDefaultWidth(), Consts.getDefaultHeight());
    ScreenManager screenManager = new ScreenManager();

    Stage liveSessionStage = new Stage();

    screenManager.layoutOnScreen(liveSessionStage);

    liveSessionStage.setTitle("ControlView");
    liveSessionStage.setScene(scene);
    liveSessionStage.toFront();
    liveSessionStage.initStyle(StageStyle.UNDECORATED);
    liveSessionStage.show();
    root.show();

  }
}
