package org.adonai.ui.screens;

import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import org.adonai.ui.JavaFxApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScreenManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(ScreenManager.class);


  private Screen primary;

  private List<Screen> externalScreens = new ArrayList<>();

  public ScreenManager () {
    primary = Screen.getPrimary();

    for (Screen next: Screen.getScreens()) {
      if (! primary.equals(next))
        externalScreens.add(next);
    }

    if (externalScreens.size() > 1)
      throw new IllegalStateException("Multi screen setups with more than one external monitors are currently not supported");

    LOGGER.info("Primary screen:  " + primary.toString() + "(" + primary.getBounds() + ")");
    for (Screen nextExternalScreen: externalScreens) {
      LOGGER.info("External screen:  " + nextExternalScreen.toString() + "(" + nextExternalScreen.getBounds() + ")");
    }

  }

  public Screen getExternalOrPrimaryScreen () {
    return ! externalScreens.isEmpty() ? externalScreens.get(0) : primary;
  }

  public Screen getPrimary () {
    return primary;
  }

  public void layoutOnScreen (final Stage stage) {
    Screen externalOrPrimary = getExternalOrPrimaryScreen();
    stage.setX(externalOrPrimary.getVisualBounds().getMinX());
    stage.setY(externalOrPrimary.getVisualBounds().getMinY());
    stage.setWidth(externalOrPrimary.getVisualBounds().getWidth());
    stage.setHeight(externalOrPrimary.getVisualBounds().getHeight());
  }

  public void layoutOnScreen (final Stage stage, int border) {
    Screen externalOrPrimary = getExternalOrPrimaryScreen();
    stage.setX(externalOrPrimary.getVisualBounds().getMinX() + border);
    stage.setY(externalOrPrimary.getVisualBounds().getMinY() + border);
    stage.setWidth(externalOrPrimary.getVisualBounds().getWidth() - (border * 2));
    stage.setHeight(externalOrPrimary.getVisualBounds().getHeight() - (border * 2));
  }





}
