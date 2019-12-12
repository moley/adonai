package org.adonai.ui.search;

import java.util.Arrays;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.stage.Stage;
import org.adonai.actions.SearchAction;
import org.adonai.model.NamedElement;
import org.adonai.model.Song;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchPageTester extends Application {

  private static final Logger LOGGER = LoggerFactory.getLogger(SearchPageTester.class.getName());


  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    SearchAction searchAction = new SearchAction();
    Song song1 = new Song();
    song1.setTitle("Song1");
    Song song2 = new Song();
    song2.setTitle("Song1");
    Song song3 = new Song();
    song3.setTitle("Song1");
    FilteredList<NamedElement> filteredList = new FilteredList<NamedElement>(FXCollections.observableArrayList(Arrays.asList(song1, song2, song3)));
    searchAction.open(filteredList, "", 10, 10);

  }
}
