package org.adonai.actions;

import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import org.adonai.ApplicationEnvironment;
import org.adonai.fx.Mask;
import org.adonai.fx.MaskLoader;
import org.adonai.fx.search.SearchController;
import org.adonai.model.NamedElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchAction<T extends NamedElement> {

  private static final Logger LOGGER = LoggerFactory.getLogger(SearchAction.class);

  public final static int SEARCHDIALOG_WIDTH = 400;
  public final static int SEARCHDIALOG_HEIGHT = 800;

  private ApplicationEnvironment applicationEnvironment;
  private Callback<ListView<T>, ListCell<T>> listViewListCellCallback;
  private EventHandler<WindowEvent> onCloseEvent;

  private MaskLoader<SearchController<T>> maskLoader = new MaskLoader<>();

  private Mask<SearchController<T>> searchMask;

  public void open(final ApplicationEnvironment applicationEnvironment,
      Callback<ListView<T>, ListCell<T>> listViewListCellCallback,
      EventHandler<WindowEvent> onCloseEvent,
      FilteredList<T> filteredList, String preset, double x, double y) {

    this.applicationEnvironment = applicationEnvironment;
    this.listViewListCellCallback = listViewListCellCallback;
    this.onCloseEvent = onCloseEvent;
    searchMask = maskLoader.loadWithStage("search");

    LOGGER.info("Open searchAction with " + filteredList.size() + " songs");

    searchMask.setSize(SEARCHDIALOG_WIDTH, SEARCHDIALOG_HEIGHT);
    searchMask.getController().setListViewListCellCallback(listViewListCellCallback);
    searchMask.getController().setFilteredSongList(filteredList);
    searchMask.getController().setPreset(preset);
    searchMask.getController().setStage(searchMask.getStage());
    searchMask.setPosition(x, y);
    searchMask.getStage().setOnHiding(onCloseEvent);
    searchMask.show();
    LOGGER.info("Search mask available at " + searchMask.getStage().getX() + "-" + searchMask.getStage().getY());
  }

  public T getSelectedItem() {
    return searchMask.getController().getSelectedElement();
  }
}
