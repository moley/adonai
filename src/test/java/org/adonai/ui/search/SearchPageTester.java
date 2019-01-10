package org.adonai.ui.search;

import javafx.application.Application;
import javafx.stage.Stage;
import org.adonai.actions.SearchAction;

import java.util.logging.Logger;

public class SearchPageTester extends Application {

  private static final Logger LOGGER = Logger.getLogger(SearchPageTester.class.getName());


  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    SearchAction searchAction = new SearchAction();
    searchAction.open();

  }
}
