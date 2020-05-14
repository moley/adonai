package org.adonai.fx.editcontent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.adonai.export.ExportToken;
import org.adonai.fx.Consts;
import org.adonai.fx.ContentChangeableController;
import org.adonai.fx.editor.TextRenderer;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.reader.text.TextfileReader;
import org.adonai.reader.text.TextfileReaderParam;

public class EditContentController extends ContentChangeableController {

  public TextArea txaText;
  public Button btnSave;
  public Button btnCancel;
  private ExportToken exportToken;

  private TextRenderer textRenderer =  new TextRenderer();


  public ExportToken getExportToken() {
    return exportToken;
  }

  public void setExportToken(ExportToken exportToken) {
    this.exportToken = exportToken;
    SongPart songPart = exportToken.getSong().findSongPart(exportToken.getSongStructItem());
    txaText.setText(textRenderer.getRenderedText(songPart));

    btnSave.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        List<String> lines = new ArrayList<>(Arrays.asList(txaText.getText().split("\n")));
        lines.add(0, "[" + songPart.getSongPartTypeLabel() + "]");
        TextfileReaderParam textfileReaderParam = new TextfileReaderParam();
        textfileReaderParam.setEmptyLineIsNewPart(false);
        textfileReaderParam.setWithTitle(false);
        TextfileReader textfileReader = new TextfileReader();
        Song song = textfileReader.read(lines, textfileReaderParam);
        SongPart newSongPart = song.getFirstPart();
        songPart.setLines(newSongPart.getLines());

        if (getOnSongContentChange() != null)
          getOnSongContentChange().handle(event);

        getStage().close();

      }
    });
    btnCancel.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        getStage().close();
      }
    });
  }

  @FXML
  public void initialize () {
    btnSave.setGraphic(Consts.createIcon("far-save", Consts.ICON_SIZE_TOOLBAR));
    btnCancel.setGraphic(Consts.createIcon("far-window-close", Consts.ICON_SIZE_TOOLBAR));
  }

}
