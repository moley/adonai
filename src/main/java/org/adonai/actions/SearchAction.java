package org.adonai.actions;

import org.adonai.ui.Mask;
import org.adonai.ui.MaskLoader;

public class SearchAction {

  public final static int SEARCHDIALOG_WIDTH = 800;
  public final static int SEARCHDIALOG_HEIGHT = 400;

  public void open () {
    MaskLoader maskLoader = new MaskLoader();
    Mask searchMask = maskLoader.load("search");
    searchMask.setSize(SEARCHDIALOG_WIDTH, SEARCHDIALOG_HEIGHT);
    searchMask.show();
  }
}
