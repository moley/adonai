package org.adonai.actions;

import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import org.adonai.ui.Mask;
import org.adonai.ui.MaskLoader;

public class ConfigurationAction {

  public final static int CONFIGDIALOG_WIDTH = 1200;
  public final static int CONFIGDIALOG_HEIGHT = 950;


  public void openConfigurations (final Double x, final Double y, final EventHandler<WindowEvent> onHiding ) {

    MaskLoader maskLoader = new MaskLoader();
    Mask searchMask = maskLoader.load("settings");
    searchMask.setSize(CONFIGDIALOG_WIDTH, CONFIGDIALOG_HEIGHT);
    searchMask.setPosition(x, y);
    searchMask.show();

    searchMask.getStage().setOnHiding(onHiding);


  }
}
