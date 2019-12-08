package org.adonai.ui;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;

public class NodeUtils {

  public <T extends Pane> List<Node> paneNodes(Node parent) {
    return paneNodes(parent, new ArrayList<Node>());
  }

  private  <T extends Pane> List<Node> paneNodes(Node parent, List<Node> nodes) {
    if (parent instanceof Pane) {
      for (Node node : ((Pane)parent).getChildren()) {
        if (node instanceof TitledPane) {
          paneNodes(((TitledPane) node).getContent(), nodes);
        }
        if (node instanceof Pane) {
          paneNodes((Pane) node, nodes);
        } else {
          nodes.add(node);
        }
      }
    }
    else if (parent instanceof TitledPane) {
      TitledPane titledPane = (TitledPane) parent;
      paneNodes(titledPane.getContent());
    }

    return nodes;
  }

}
