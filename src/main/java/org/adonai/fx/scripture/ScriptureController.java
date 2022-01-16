package org.adonai.fx.scripture;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.adonai.bibles.Bible;
import org.adonai.bibles.BibleBook;
import org.adonai.bibles.BibleContainer;
import org.adonai.bibles.BibleService;
import org.adonai.bibles.Bibles;
import org.adonai.bibles.Book;
import org.adonai.bibles.Chapter;
import org.adonai.bibles.Verse;
import org.adonai.fx.AbstractController;

public class ScriptureController extends AbstractController {
  @FXML private TextField txtSearch;
  @FXML private ComboBox<Bibles> cboBible;
  @FXML private ComboBox<Book> cboBook;

  @FXML private VBox panContent;

  private BibleService bibleService = new BibleService();

  private String reference;

  private BibleContainer bibleContainer = new BibleContainer();

  /**
   * #LUTHER_1912,DEUTERONOMY(5,12-14)
   * #LUTHER_1912,DEUTERONOMY(5,12)
   * #LUTHER_1912,DEUTERONOMY(5,12-6,12)
   */

  public void initialize () {
    bibleContainer = bibleService.getAllBibles();

    cboBible.setItems(FXCollections.observableArrayList(Bibles.values()));
    cboBible.getSelectionModel().selectFirst();
    cboBible.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> renderContent(cboBook.getSelectionModel().getSelectedItem()));

    cboBook.setItems(FXCollections.observableArrayList(Book.values()));
    cboBook.getSelectionModel().selectFirst();
    cboBook.getSelectionModel().selectedItemProperty().addListener(
        (observable, oldValue, newValue) -> renderContent(newValue));

    renderContent(cboBook.getSelectionModel().getSelectedItem());

    txtSearch.setOnKeyReleased(new EventHandler<KeyEvent>() {
      @Override public void handle(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER))
          renderContent(cboBook.getSelectionModel().getSelectedItem());
      }
    });

  }



  void renderContent (Book book) {
    reference = "#" + cboBible.getSelectionModel().getSelectedItem().name() + "," + cboBook.getSelectionModel().getSelectedItem().name() + "(" + txtSearch.getText() + ")";

    Bible bible = bibleContainer.getBible(cboBible.getSelectionModel().getSelectedItem());
    BibleBook bibleBook = bible.findBook(book);
    if (book != null) {
      panContent.getChildren().clear();
      for (Chapter chapter: bibleBook.getChapters()) {
        Label chapterLabel = new Label(String.valueOf(chapter.getNumber()));
        chapterLabel.getStyleClass().setAll("scriptureChapterNumber");
        panContent.getChildren().add(chapterLabel);
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
}
