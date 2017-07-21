package org.adonai.ui.editor;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import org.adonai.Key;

public class SongDetailsController {


  private PartEditor partEditor;
  @FXML
  private ComboBox<Key> cboCurrentKey;

  @FXML
  private ComboBox<Key> cboOriginalKey;


  public void setPartEditor(PartEditor partEditor) {
    this.partEditor = partEditor;
  }

}
