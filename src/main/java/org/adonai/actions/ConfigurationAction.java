package org.adonai.actions;

import org.adonai.ui.Consts;
import org.adonai.ui.Mask;
import org.adonai.ui.MaskLoader;

public class ConfigurationAction {

  public final static int CONFIGDIALOG_WIDTH = Consts.DEFAULT_WIDTH;
  public final static int CONFIGDIALOG_HEIGHT = Consts.DEFAULT_HEIGHT;


  public void openConfigurations () {

    MaskLoader maskLoader = new MaskLoader();
    Mask searchMask = maskLoader.load("settings");
    searchMask.setSize(CONFIGDIALOG_WIDTH, CONFIGDIALOG_HEIGHT);
    searchMask.show();

  }
}
