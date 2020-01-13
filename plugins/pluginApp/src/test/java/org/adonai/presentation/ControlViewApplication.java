package org.adonai.presentation;

import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.adonai.SizeInfo;
import org.adonai.export.ExportConfiguration;
import org.adonai.export.ExportToken;
import org.adonai.export.presentation.PresentationDocumentBuilder;
import org.adonai.export.presentation.PresentationExporter;
import org.adonai.model.Configuration;
import org.adonai.model.ConfigurationService;
import org.adonai.ui.Consts;
import org.adonai.ui.UiUtils;
import org.adonai.ui.screens.ScreenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControlViewApplication extends Application {

  private static final Logger LOGGER = LoggerFactory.getLogger(ControlViewApplication.class);


  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException, InterruptedException {

    System.setProperty("user.home", System.getProperty("user.home"));
    ScreenManager screenManager = new ScreenManager();
    ConfigurationService configurationService = new ConfigurationService();



    SizeInfo sizeInfo = new SizeInfo(Consts.getDefaultWidth(), Consts.getDefaultHeight());
    PresentationExporter exporter = new PresentationExporter(sizeInfo);

    Configuration configuration = configurationService.get();
    ExportConfiguration exportConfiguration = configuration.findDefaultExportConfiguration(PresentationDocumentBuilder.class);

    LOGGER.info("Load from " + configurationService.getConfigFile().getAbsolutePath());
    exporter.export(configuration.getSongBooks().get(0).getSongs(), null, exportConfiguration);


    for (ExportToken next: exporter.getExportTokenContainer().getExportTokenList()) {
      LOGGER.info("Next: " + next.getText() + " - " + next.getAreaInfo() + " - " + next.getExportTokenType());
    }

    ControlView root = new ControlView(exporter.getPanes());
    Scene scene = new Scene(root, Consts.getDefaultWidth(), Consts.getDefaultHeight());
    screenManager.layoutOnScreen(primaryStage);

    primaryStage.setTitle("ControlView");
    primaryStage.setScene(scene);
    primaryStage.setMaximized(true);
    primaryStage.setAlwaysOnTop(true);
    primaryStage.initStyle(StageStyle.UNDECORATED);
    primaryStage.show();
    root.show();


  }

}