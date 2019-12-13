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


  public <T extends Pane> List<Node> paneNodes(Node parent) {
    return paneNodes(parent, new ArrayList<Node>());
  }

  public void requestFocusOnSceneAvailable(Node node) {
    if (node.getScene() == null) {
      node.sceneProperty().addListener(new ChangeListener<Scene>() {
        @Override public void changed(ObservableValue<? extends Scene> observable, Scene oldValue, Scene newValue) {
          if (newValue != null) {
            node.sceneProperty().removeListener(this);
            node.requestFocus();
          }

        }
      });

    } else
      node.requestFocus();

  }

  public void requestFocusOnSceneAvailable(TextInputControl node, final boolean select) {
    requestFocusOnSceneAvailable(node, select, -1);
  }

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

  private <T extends Pane> List<Node> paneNodes(Node parent, List<Node> nodes) {
    if (parent instanceof Pane) {
      for (Node node : ((Pane) parent).getChildren()) {
        if (node instanceof TitledPane) {
          paneNodes(((TitledPane) node).getContent(), nodes);
        }
        if (node instanceof Pane) {
          paneNodes((Pane) node, nodes);
        } else {
          nodes.add(node);
        }
      }
    } else if (parent instanceof TitledPane) {
      TitledPane titledPane = (TitledPane) parent;
      paneNodes(titledPane.getContent());
    }

    return nodes;
  }

}
