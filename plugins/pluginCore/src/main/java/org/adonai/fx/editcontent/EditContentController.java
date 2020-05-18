package org.adonai.fx.editcontent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.adonai.export.ExportToken;
import org.adonai.fx.ContentChangeableController;
import org.adonai.fx.editor.TextRenderer;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.reader.text.TextfileReader;
import org.adonai.reader.text.TextfileReaderParam;

public class EditContentController extends ContentChangeableController {

  @FXML
  private TextArea txaText;

  private ExportToken exportToken;

  private TextRenderer textRenderer =  new TextRenderer();

  private SongPart songPart;


  public void setExportToken(ExportToken exportToken) {
    this.exportToken = exportToken;
    songPart = exportToken.getSong().findSongPart(exportToken.getSongStructItem());
    txaText.setText(textRenderer.getRenderedText(songPart));
  }

  @Override protected void save() {
    List<String> lines = new ArrayList<>(Arrays.asList(txaText.getText().split("\n")));
    lines.add(0, "[" + songPart.getSongPartTypeLabel() + "]");
    TextfileReaderParam textfileReaderParam = new TextfileReaderParam();
    textfileReaderParam.setEmptyLineIsNewPart(false);
    textfileReaderParam.setWithTitle(false);
    TextfileReader textfileReader = new TextfileReader();
    Song song = textfileReader.read(lines, textfileReaderParam);
    SongPart newSongPart = song.getFirstPart();
    songPart.setLines(newSongPart.getLines());
  }

}
