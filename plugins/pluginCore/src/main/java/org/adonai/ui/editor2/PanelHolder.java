package org.adonai.ui.editor2;

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
    if (index == null)
      throw new IllegalStateException("Set an index before you set the panel");
    panel.setId(index);
  }

  Parent panel;

  public String getIndex() {
    return index;
  }

  public void setIndex(String index) {
    this.index = index;
  }

  String index;

}
