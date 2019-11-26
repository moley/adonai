package org.adonai.ui.editor2;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Window;
import org.adonai.Chord;
import org.adonai.model.LinePart;
import org.adonai.services.AddChordService;
import org.adonai.services.RemoveChordService;
import org.adonai.services.SongCursor;

/**
 * Created by OleyMa on 17.01.17.
 */
public class ChordEditor {

  private TextField txtChord;

  private LinePartEditor linePartEditor;

  private Popup popup;

  private AddChordService addChordService = new AddChordService();

  private RemoveChordService removeChordService = new RemoveChordService();

  private boolean originalChord;

  public ChordEditor(final LinePartEditor linePartEditor) {
    this.linePartEditor = linePartEditor;
    popup = new Popup();
    VBox dialogVbox = new VBox(20);

    txtChord = new TextField("");
    txtChord.setId("chordeditor");


    txtChord.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {

        if (event.getCode() == KeyCode.ENTER) {
          SongCursor songCursor = linePartEditor.getCursor();
          SongEditor songEditor = linePartEditor.getSongEditor();
          if (linePartEditor.getTxtText().getCaretPosition() > 0) { //if we are not below an existing chord
            if (! txtChord.getText().trim().isEmpty()) {

              LinePart selectedLinePart;

              if (originalChord)
                selectedLinePart = addChordService.addChord(songCursor, null, new Chord(txtChord.getText()));
              else
                selectedLinePart = addChordService.addChord(songCursor, new Chord(txtChord.getText()), null);

              songEditor.reload().getPartEditor(selectedLinePart).requestFocus(false);
            }
          }
          else {
            if (!txtChord.getText().trim().isEmpty()) {
              Chord chord = new Chord(txtChord.getText());
              if (originalChord)
                linePartEditor.getLinePart().setOriginalChord(chord.toString());
              else
                linePartEditor.getLinePart().setChord(chord.toString());
              linePartEditor.getLblChord().setText(txtChord.getText());
            } else {
              LinePart selectedLinePart = removeChordService.removeChord(songCursor);
              LinePartEditor selectedLinePartEditor = songEditor.reload().getPartEditor(selectedLinePart);

              selectedLinePartEditor.requestFocus(false);
              if (songCursor.getPositionInLinePart() != null) {
                Platform.runLater(new Runnable() {

                  @Override
                  public void run() {
                    selectedLinePartEditor.getTxtText().positionCaret(songCursor.getPositionInLinePart());
                  }
                });
              }


            }
          }
          popup.hide();
        }

        if (event.getCode() == KeyCode.ESCAPE)
          popup.hide();
      }
    });
    dialogVbox.getChildren().add(txtChord);
    popup.getContent().add(dialogVbox);
  }

  public void show (final boolean originalChord) {
    this.originalChord = originalChord;

    TextField txt = linePartEditor.getTxtText();
    Point2D locationCaret = txt.getInputMethodRequests().getTextLocation(0);

    if (linePartEditor.getTxtText().getCaretPosition() == 0)
      txtChord.setText(originalChord ? linePartEditor.getLinePart().getOriginalChord() : linePartEditor.getLinePart().getChord());
    else
      txtChord.setText("");

    System.out.println ("Position " + locationCaret.getX() + "-" + locationCaret.getY() + "(Caret " + txt.getCaretPosition() + ")");
    System.out.println ("Textfield: " + txt.getText());

    Window window = txt.getScene().getWindow();

    double x = locationCaret.getX() + txt.getLayoutX(); //txt.getLayoutX() + window.getX() + locationCaret.getX();
    double y = locationCaret.getY() - txt.getHeight() - txt.getHeight(); //txt.getLayoutY() + window.getY() + locationCaret.getY();
    popup.show(window, x, y);
  }


}
