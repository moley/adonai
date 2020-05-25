package org.adonai.actions;

import javafx.collections.transformation.FilteredList;
import org.adonai.ApplicationEnvironment;
import org.adonai.fx.Mask;
import org.adonai.fx.MaskLoader;
import org.adonai.model.NamedElement;
import org.adonai.ui.search.SearchController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchAction {

  private static final Logger LOGGER = LoggerFactory.getLogger(SearchAction.class);


  public final static int SEARCHDIALOG_WIDTH = 300;
  public final static int SEARCHDIALOG_HEIGHT = 50;

  private final ApplicationEnvironment applicationEnvironment;

  public SearchAction (final ApplicationEnvironment applicationEnvironment) {
    this.applicationEnvironment = applicationEnvironment;
  }

  public void open (FilteredList<? extends NamedElement> filteredList, String preset, double x, double y) {
    LOGGER.info("Open searchAction");

    MaskLoader<SearchController> maskLoader = new MaskLoader<SearchController>();
    Mask<SearchController> searchMask = maskLoader.load("search");
    searchMask.setSize(SEARCHDIALOG_WIDTH, SEARCHDIALOG_HEIGHT);
    searchMask.getController().setFilteredSongList(filteredList);
    searchMask.getController().setPreset(preset);
    searchMask.setPosition(x, y);
    searchMask.show();
    LOGGER.info("Search mask available at " + searchMask.getStage().getX() + "-" + searchMask.getStage().getY());
  }
}
