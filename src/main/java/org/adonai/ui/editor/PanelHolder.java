package org.adonai.ui.editor;

import javafx.scene.Parent;

/**
 * Created by OleyMa on 30.11.16.
 */
public abstract class PanelHolder {

  public Parent getPanel() {
    return panel;
  }

  public void setPanel(Parent panel) {
    this.panel = panel;
  }

  Parent panel;

}
