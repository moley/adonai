package org.adonai.ui;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import org.adonai.ui.editor2.SongEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NodeUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(NodeUtils.class);



  public void requestFocusOnSceneAvailable(TextInputControl node, final boolean select, final int positionCaret) {
    if (node.getScene() == null) {
      node.sceneProperty().addListener(new ChangeListener<Scene>() {
        @Override public void changed(ObservableValue<? extends Scene> observable, Scene oldValue, Scene newValue) {
          if (newValue != null) {
            LOGGER.info("requestFocusOnSceneAvailable (" + node.getId() + ", " + select + ", " +  positionCaret + ")");

            node.sceneProperty().removeListener(this);
            node.requestFocus();
            if (select)
              node.selectAll();
            else
              node.deselect();

            if (positionCaret >= 0)
              node.positionCaret(positionCaret);
          }

        }
      });

    } else
      node.requestFocus();

  }


}
