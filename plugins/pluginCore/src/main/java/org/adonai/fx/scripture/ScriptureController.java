package org.adonai.fx.scripture;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.adonai.StringUtils;
import org.adonai.bibles.Bible;
import org.adonai.bibles.BibleBook;
import org.adonai.bibles.BibleService;
import org.adonai.bibles.Bibles;
import org.adonai.bibles.Book;
import org.adonai.bibles.Chapter;
import org.adonai.bibles.Verse;
import org.adonai.fx.AbstractController;
import org.adonai.fx.renderer.ScriptureChapterCellRenderer;
import org.adonai.fx.renderer.ScriptureChapterStringConverter;

public class ScriptureController extends AbstractController {
  @FXML private ComboBox<Bibles> cboBible;
  @FXML private ComboBox<Book> cboBook;
  @FXML private ComboBox<Chapter> cboChapters;

  @FXML private VBox panContent;

  private BibleService bibleService = new BibleService();

  public void initialize () {
    cboBible.setItems(FXCollections.observableArrayList(Bibles.values()));
    cboBible.getSelectionModel().selectFirst();
    cboBible.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> renderChapters(cboBible.getSelectionModel().getSelectedItem(), cboBook.getSelectionModel().getSelectedItem()));

    cboBook.setItems(FXCollections.observableArrayList(Book.values()));
    cboBook.getSelectionModel().selectFirst();
    cboBook.getSelectionModel().selectedItemProperty().addListener(
        (observable, oldValue, newValue) -> renderChapters(cboBible.getSelectionModel().getSelectedItem(), newValue));

    renderChapters(cboBible.getSelectionModel().getSelectedItem(), cboBook.getSelectionModel().getSelectedItem());

    cboChapters.setCellFactory(cellfactory -> new ScriptureChapterCellRenderer());
    cboChapters.setConverter(new ScriptureChapterStringConverter());
    cboChapters.getSelectionModel().selectedItemProperty().addListener(
        (observable, oldValue, newValue) -> renderContent(newValue));

  }

  void renderChapters (Bibles bibles, Book book) {
    Bible bible = bibleService.getBible(bibles);
    BibleBook bibleBook = bible.findBook(book);
    cboChapters.setItems(FXCollections.observableArrayList(bibleBook.getChapters()));
    cboChapters.getSelectionModel().selectFirst();
    renderContent(cboChapters.getSelectionModel().getSelectedItem());
  }

  void renderContent (Chapter chapter) {
    if (chapter != null) {
      panContent.getChildren().clear();
      for (Verse verse : chapter.getVerses()) {
        HBox verseBox = new HBox(10);
        Label lblVerseNumber = new Label(String.valueOf(verse.getNumber()));
        lblVerseNumber.getStyleClass().setAll("scriptureVerseNumber");
        Text text = new Text(verse.getText());
        text.wrappingWidthProperty().set(600);
        text.getStyleClass().setAll("scriptureContent");

        verseBox.getChildren().addAll(lblVerseNumber, text);
        panContent.getChildren().add(verseBox);
      }
    }

  }
}
