package org.adonai.ui.imports.pages;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.adonai.model.ConfigurationService;
import org.adonai.ui.editor2.SongEditor;
import org.adonai.ui.imports.SongImportController;

public class PreviewPage extends WizardPage {

  public final static String TITLE = "Preview";
  public PreviewPage(SongImportController controller) {
    super(TITLE, controller);
  }

  Parent getContent() {
    VBox rootpanel = new VBox();

    VBox.setVgrow(rootpanel, Priority.ALWAYS);
    rootpanel.sceneProperty().addListener(new ChangeListener<Scene>() {
      @Override
      public void changed(ObservableValue<? extends Scene> observable, Scene oldValue, Scene newValue) {
        System.out.println ("Visibility change: " + oldValue + "->" + newValue);
        if (oldValue == null && newValue != null) {
          System.out.println ("Preview for " + controller.getSongToImport().toString() );
          ConfigurationService configurationService = new ConfigurationService();

          SongEditor songEditor = new SongEditor(configurationService.get(), controller.getSongToImport());
          rootpanel.getChildren().clear();
          rootpanel.getChildren().add(songEditor.getPanel());
        }
      }
    });
    return rootpanel;
  }
}
