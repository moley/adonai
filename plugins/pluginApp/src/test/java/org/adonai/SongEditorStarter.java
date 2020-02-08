package org.adonai;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.adonai.model.Configuration;
import org.adonai.model.ConfigurationService;
import org.adonai.model.Song;
import org.adonai.model.SongBook;
import org.adonai.ui.Consts;
import org.adonai.ui.UiUtils;
import org.adonai.ui.editor2.SongEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by OleyMa on 22.11.16.
 */
public class SongEditorStarter extends Application {

  private static final Logger LOGGER = LoggerFactory.getLogger(SongEditorStarter.class);

  private AdonaiProperties adonaiProperties = new AdonaiProperties();



  private static int songnumber = 0;

    public static void main(String[] args) {
      if (args.length > 0)
        SongEditorStarter.songnumber = Integer.valueOf(args[0]).intValue();

      launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

      File tmpConfig = new File("build/config.xml");

      ConfigurationService configurationService = new ConfigurationService();
      Configuration configuration = configurationService.get(adonaiProperties.getCurrentTenant());
      SongBook songBook = configuration.getSongBooks().get(0);
      Song song = songBook.getSongs().get(songnumber);
      SongEditor songEditor = new SongEditor(configuration, song);

      Parent editor = songEditor.getPanel();



      Scene scene = new Scene(editor, Consts.getDefaultWidth(), Consts.getDefaultHeight(), false);

      UiUtils.applyCss(scene);

      primaryStage.setScene(scene);

      primaryStage.show();

      primaryStage.setOnHiding(new EventHandler<WindowEvent>() {
        @Override public void handle(WindowEvent event) {
          configurationService.setConfigFile(tmpConfig);
          configurationService.save(adonaiProperties.getCurrentTenant());

          LOGGER.info("Save configuration in " + configurationService.getConfigFile(adonaiProperties.getCurrentTenant()).getAbsolutePath());

        }
      });


    }

}
