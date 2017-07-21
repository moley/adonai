package org.adonai.screens;

import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ScreenManager {

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





}
