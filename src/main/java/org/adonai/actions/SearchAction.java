package org.adonai.actions;

import javafx.collections.transformation.FilteredList;
import org.adonai.model.NamedElement;
import org.adonai.model.Song;
import org.adonai.ui.Mask;
import org.adonai.ui.MaskLoader;
import org.adonai.ui.search.SearchController;

public class SearchAction {

  public final static int SEARCHDIALOG_WIDTH = 300;
  public final static int SEARCHDIALOG_HEIGHT = 50;

  public void open (FilteredList<? extends NamedElement> filteredList, String preset, double x, double y) {
    MaskLoader<SearchController> maskLoader = new MaskLoader<SearchController>();
    Mask<SearchController> searchMask = maskLoader.load("search");
    searchMask.setSize(SEARCHDIALOG_WIDTH, SEARCHDIALOG_HEIGHT);
    searchMask.getController().setFilteredSongList(filteredList);
    searchMask.getController().setPreset(preset);
    searchMask.setPosition(x, y);
    searchMask.show();
  }
}
