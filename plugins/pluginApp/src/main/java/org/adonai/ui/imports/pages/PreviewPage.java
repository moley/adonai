package org.adonai.ui.imports.pages;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.adonai.ApplicationEnvironment;
import org.adonai.model.Configuration;
import org.adonai.ui.editor2.SongEditor;
import org.adonai.ui.imports.SongImportController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PreviewPage extends WizardPage {

  private static final Logger LOGGER = LoggerFactory.getLogger(PreviewPage.class);

  private Configuration configuration;


  public final static String TITLE = "Preview";
  public PreviewPage(ApplicationEnvironment applicationEnvironment, SongImportController controller, Configuration configuration) {
    super(applicationEnvironment, TITLE, controller);
    this.configuration = configuration;
  }

  Parent getContent() {
    VBox rootpanel = new VBox();
    rootpanel.setUserData("importsongwizard.rootpanel");

    VBox.setVgrow(rootpanel, Priority.ALWAYS);
    rootpanel.sceneProperty().addListener(new ChangeListener<Scene>() {
      @Override
      public void changed(ObservableValue<? extends Scene> observable, Scene oldValue, Scene newValue) {
        if (LOGGER.isDebugEnabled())
          LOGGER.debug("Visibility change: " + oldValue + "->" + newValue);
        if (oldValue == null && newValue != null) {
          LOGGER.info("Preview for " + controller.getSongToImport().toString() );

          SongEditor songEditor = new SongEditor(applicationEnvironment, configuration, controller.getSongToImport());
          rootpanel.getChildren().clear();
          rootpanel.getChildren().add(songEditor.getPanel());
        }
      }
    });
    return rootpanel;
  }
}
