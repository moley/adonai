package org.adonai.ui.editor2;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.services.MergeLineService;
import org.adonai.services.MoveChordService;
import org.adonai.services.SongCursor;
import org.adonai.services.SongNavigationService;
import org.adonai.services.SplitLineService;

public class LinePartEditor extends PanelHolder {

  private TextField txtText;

  public TextField getTxtText() {
    return txtText;
  }

  public Label getLblChord() {
    return lblChord;
  }

  private Label lblChord;
  private Label lblChordOriginal;
  private LineEditor lineEditor;
  private LinePart linePart;

  private VBox root = new VBox();


  private MoveChordService moveChordService = new MoveChordService();

  private SplitLineService splitLineService = new SplitLineService();

  private SongNavigationService songNavigationService = new SongNavigationService();

  private MergeLineService mergeLineService = new MergeLineService();

  private boolean editable;




  private LinePartEditor getEditor () {
    return this;
  }

  public LinePart getLinePart () {
    return linePart;
  }

  public LineEditor getLineEditor () {
    return lineEditor;
  }

  public void toHome () {
    requestFocus(false);
    txtText.positionCaret(0);
  }

  public void toEnd () {
    txtText.requestFocus();
    txtText.positionCaret(txtText.getText().length() - 1);
  }

  public void requestFocus (final boolean select) {
    System.out.println ("Request focus txtField " + txtText.getText());
    Platform.runLater(new Runnable() {

      @Override
      public void run() {
        txtText.requestFocus();
        if (select)
          txtText.selectAll();
        else
          txtText.deselect();

      }
    });

  }

  public void requestFocusAndSetCaret (final boolean select, final Integer newCaretPosition) {
    System.out.println ("Request focus txtField " + txtText.getText());
    txtText.requestFocus();
    if (select)
      txtText.selectAll();
    else
      txtText.deselect();

    if (newCaretPosition != null) {
      System.out.println ("isFocused: " + txtText.isFocused());

      System.out.println ("set caret position to " + newCaretPosition);
      txtText.positionCaret(newCaretPosition);

    }

  }

  private void adaptChordLabel () {
    if (txtText.getCaretPosition() == 0 && txtText.isFocused()) {
      lblChord.setId("chordlabel_selected");
      lblChordOriginal.setId("chordlabel_selected");
    }
    else {
      lblChord.setId("chordlabel");
      lblChordOriginal.setId("chordlabel");
    }
  }

  public LinePartEditor (final LineEditor lineEditor, final LinePart linePart, final boolean editable, final String id) {
    this.editable = editable;
    this.index = id;
    this.lineEditor = lineEditor;
    this.linePart = linePart;

    lblChord = new Label();
    lblChord.setText(linePart.getChord());
    lblChord.setId("chordlabel");



    lblChordOriginal = new Label();
    lblChordOriginal.setText(linePart.getOriginalChord());
    lblChordOriginal.setId("chordlabel");

    txtText = new TextField(linePart.getText());

    txtText.setId("texteditor");

    //Debug layouting
/**    lblChord.setStyle("-fx-border-color: red;");
    lblChordOriginal.setStyle("-fx-border-color: red;");
    txtText.setStyle("-fx-border-color: blue;");
    root.setStyle("-fx-border-color:green;");**/

    txtText.prefColumnCountProperty().bind(txtText.textProperty().length());


    ChordEditor chordEditor = new ChordEditor(this);

    if (editable) {

      txtText.textProperty().bindBidirectional(linePart.textProperty());
      lblChord.textProperty().bindBidirectional(linePart.chordProperty());
      lblChordOriginal.textProperty().bindBidirectional(linePart.originalChordProperty());

      txtText.deselect();

      txtText.caretPositionProperty().addListener(new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
          adaptChordLabel();
        }
      });
      txtText.focusedProperty().addListener(new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
          adaptChordLabel();
        }
      });

      txtText.setOnKeyReleased(new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
          System.out.println("onKeyReleased - Ctrl: " + event.isControlDown() + "- Command " + event.isMetaDown() + "-Shift: " + event.isShiftDown() + "- Event " + event.getCode() + "-" + event.getText());

        }
      });

      txtText.setOnKeyPressed(new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
          System.out.println("onKeyPressed - Ctrl: " + event.isControlDown() + "- Command " + event.isMetaDown() + "-Shift: " + event.isShiftDown() + "- Event " + event.getCode() + "-" + event.getText());


          if (event.getCode() == KeyCode.ESCAPE) {
            //throw new IllegalStateException("NYI");
            //getSongEditor().getPartStructure().requestFocus();
          }

          if (event.getCode() == KeyCode.DOWN) {
            LinePart focusedLinePart = songNavigationService.stepToNextLine(getCursor());
            getSongEditor().getPartEditor(focusedLinePart).requestFocus(false);
          }

          if (event.getCode() == KeyCode.UP) {
            LinePart focusedLinePart = songNavigationService.stepToPreviousLine(getCursor());
            getSongEditor().getPartEditor(focusedLinePart).requestFocus(false);
          }

          if (event.isControlDown() && event.getCode() == KeyCode.RIGHT) {
            LinePart linePart = getCursor().getCurrentLinePart();
            moveChordService.toRight(getCursor());
            getSongEditor().reload().getPartEditor(linePart).requestFocus(false);

          }

          if (event.isControlDown() && event.getCode() == KeyCode.LEFT) {
            LinePart linePart = getCursor().getCurrentLinePart();
            moveChordService.toLeft(getCursor());
            getSongEditor().reload().getPartEditor(linePart).requestFocus(false);
          }


          if (event.isAltDown() && event.getCode() == KeyCode.RIGHT) {
            LinePart focusedLinePart = songNavigationService.stepToNextLinePart(getCursor());
            getSongEditor().getPartEditor(focusedLinePart).requestFocus(false);
          }

          if (event.isAltDown() && event.getCode() == KeyCode.LEFT) {
            LinePart focusedLinePart = songNavigationService.stepToPreviousLinePart(getCursor());
            getSongEditor().getPartEditor(focusedLinePart).requestFocus(false);
          }

          if (event.getCode() == KeyCode.BACK_SPACE && txtText.getCaretPosition() == 0) {
            LinePart focusedLinePart = mergeLineService.mergeLine(getCursor());
            getSongEditor().reload().getPartEditor(focusedLinePart).requestFocus(false);
          }

          if (event.getCode() == KeyCode.ENTER) {

            LinePart focusedLinePart = splitLineService.splitLine(getCursor());
            getSongEditor().reload().getPartEditor(focusedLinePart).requestFocus(true);
          }

          if (!event.isAltDown() && txtText.getCaretPosition() == 0 && event.getCode() == KeyCode.LEFT) {
            int currentIndex = lineEditor.getIndex(getEditor());
            if (currentIndex > 0) {
              lineEditor.getLinePartEditor(--currentIndex).toEnd();
            }
          }

          if (!event.isAltDown() && txtText.getCaretPosition() == txtText.getText().length() && event.getCode() == KeyCode.RIGHT) {
            int currentIndex = lineEditor.getIndex(getEditor());
            if (currentIndex < lineEditor.getLinePartEditors().size() - 1) {
              lineEditor.getLinePartEditor(++currentIndex).toHome();
            }
          }

          if (event.isControlDown() && event.getCode() == KeyCode.C) {

            chordEditor.show(getSongEditor().isShowOriginalChords());
          }
        }
      });
    }

    txtText.setEditable(editable);

    StackPane stack = new StackPane();
    stack.getChildren().add(lblChord);
    stack.getChildren().add(lblChordOriginal);

    toggleChordType(getSongEditor().isShowOriginalChords());

    root.setAlignment(Pos.CENTER_LEFT);
    setPanel(root);
    root.getChildren().add(stack);
    root.getChildren().add(txtText);

    stack.setAlignment(Pos.CENTER_LEFT);
    txtText.setAlignment(Pos.CENTER);

  }

  public void toggleChordType (final boolean showOrigin) {

    lblChordOriginal.setVisible(showOrigin);
    lblChord.setVisible(! showOrigin);
  }

  public PartEditor getPartEditor () {
    return lineEditor.getPartEditor();
  }

  public SongEditor getSongEditor () {
    return getPartEditor().getSongEditor();
  }

  public SongCursor getCursor () {
    SongCursor cursor = new SongCursor();
    SongPart currentSongPart = getEditor().getLineEditor().getPartEditor().getPart();
    Song currentSong = getEditor().getLineEditor().getPartEditor().getSongEditor().getSong();
    cursor.setPositionInLinePart(getEditor().getTxtText().getCaretPosition());
    cursor.setCurrentLine(lineEditor.getLine());
    cursor.setCurrentSongPart(currentSongPart);
    cursor.setCurrentSong(currentSong);
    cursor.setCurrentLinePart(linePart);
    return cursor;
  }

  public void save () {
    getLinePart().setText(txtText.getText());
    getLinePart().setChord(lblChord.getText());
  }

  public String toString () {
    return linePart.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    LinePartEditor that = (LinePartEditor) o;

    if (txtText != null ? !txtText.equals(that.txtText) : that.txtText != null) return false;
    if (lblChord != null ? !lblChord.equals(that.lblChord) : that.lblChord != null) return false;
    if (lineEditor != null ? !lineEditor.equals(that.lineEditor) : that.lineEditor != null) return false;
    if (linePart != null ? !linePart.equals(that.linePart) : that.linePart != null) return false;
    return root != null ? root.equals(that.root) : that.root == null;
  }

  public boolean hasChanged() {
    if (lblChord.getText() != null ? !lblChord.getText().equals(linePart.getChord()) : linePart.getChord() != null) return true;
    if (txtText.getText() != null ? !txtText.getText().equals(linePart.getText()) : linePart.getText() != null) return true;
    return false;
  }
}
