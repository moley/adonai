package org.adonai.fx.additionals;

import javafx.scene.control.TextArea;
import org.adonai.model.Additional;

public class AdditionalDetailTextController extends AdditionalDetailController{
  public TextArea txaContent;

  public void setAdditional(Additional additional) {
    super.setAdditional(additional);
    txaContent.setText(getAdditional().getContent());
  }

  @Override protected void save() {
    getAdditional().setContent(txaContent.getText());

  }
}
