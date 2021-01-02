package org.adonai.fx;

import java.util.ArrayList;
import java.util.List;
import javafx.stage.Screen;
import javafx.stage.Stage;
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

  public Screen getPrimary () {
    return primary;
  }

  public List<Screen> getAllScreens () {
    return Screen.getScreens();
  }


  public void layoutOnScreen (final Stage stage, Screen screen) {
    if (screen == null)
      throw new IllegalArgumentException("Parameter screen must not be null");

    stage.setX(screen.getVisualBounds().getMinX());
    stage.setY(screen.getVisualBounds().getMinY());
    stage.setWidth(screen.getVisualBounds().getWidth());
    stage.setHeight(screen.getVisualBounds().getHeight());
  }

  public void layoutOnScreen (final Stage stage, int border, Screen screen) {
    if (screen == null)
      throw new IllegalArgumentException("Parameter screen must not be null");

    stage.setX(screen.getVisualBounds().getMinX() + border);
    stage.setY(screen.getVisualBounds().getMinY() + border);
    stage.setWidth(screen.getVisualBounds().getWidth() - (border * 2));
    stage.setHeight(screen.getVisualBounds().getHeight() - (border * 2));
  }

  public Screen getScreen(String adminScreen) {
    if (getAllScreens().size() == 1)
      return getPrimary();
    if (adminScreen == null)
      return getPrimary();
    String [] tokens = adminScreen.split("#");
    if (tokens != null && tokens.length == 2) {
      for (Screen next : getAllScreens()) {
        if (Double.valueOf(tokens[0]).equals(next.getBounds().getMinX()) &&
            Double.valueOf(tokens[1]).equals(next.getBounds().getMinY())) {
          LOGGER.info("Found screen " + next + " for key " + adminScreen);
          return next;
        }
      }
    }
    return getPrimary();
  }

  public String getId (final Screen screen) {
    String id =  screen.getBounds().getMinX() + "#" + screen.getBounds().getMinY();
    LOGGER.info("ID for screen " + screen + ":" + id);
    return id;
  }
}
