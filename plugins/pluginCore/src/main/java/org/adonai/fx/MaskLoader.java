package org.adonai.fx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.adonai.ApplicationEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MaskLoader<T extends AbstractController> {

  private static final Logger LOGGER = LoggerFactory.getLogger(MaskLoader.class);

  public Mask<T> load (final String name) {
    LOGGER.info("Loading mask '" + name + "'");
    Mask<T> mask = new Mask<T>();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + name + ".fxml"));
    loader.setClassLoader(getClass().getClassLoader());
    try {
      Parent root = loader.load();
      T controller = loader.getController();

      Scene scene = new Scene(root);
      UiUtils.applyCss(scene);
      Stage stage = new Stage(StageStyle.UNDECORATED);
      stage.setScene(scene);
      mask.setRoot(root);
      mask.setStage(stage);
      mask.setScene(scene);
      mask.setController(controller);
      stage.toFront();
      stage.setResizable(false);
      stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
        if (KeyCode.ESCAPE == event.getCode()) {
          UiUtils.close(stage);
        }
      });


      return mask;
    } catch (Exception e) {
      LOGGER.error("Error loading mask " + name + ": " + e.getLocalizedMessage(), e);
      throw new IllegalStateException(e);
    }
  }
}
