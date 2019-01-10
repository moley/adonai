package org.adonai.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * Created by OleyMa on 25.10.16.
 */
public class Consts {

  private static final Logger LOGGER = Logger.getLogger(Consts.class.getName());

  public final static int ICON_SIZE_SMALL = 30;
  public final static int ICON_SIZE_MEDIUM = 50;
  public final static int ICON_SIZE_LARGE = 70;

  public final static int DEFAULT_LISTVIEW_WIDTH = 450;
  public final static int DEFAULT_WIDTH = 1400;
  public final static int DEFAULT_HEIGHT = 800;


  public final static String DEFAULT_BUNDLE = "adonai";

  public final static String ADONAI_HOME_PROP = "adonai.home";

  public final static String USER_HOME = System.getProperty(ADONAI_HOME_PROP) != null ? System.getProperty(ADONAI_HOME_PROP) : System.getProperty("user.home");
  public final static File LEGUAN_HOME = new File (USER_HOME, ".adonai");
  public final static File ADDITIONALS_PATH = new File (LEGUAN_HOME, "additionals");

  public final static ImageView createImageView (final String name, int iconSize) {
    LOGGER.info("createImageView " + name);
    return new ImageView(createImage(name, iconSize));
  }

  public final static Image createImage (String name, int iconSize) {
    LOGGER.info("createImage " + name);
    return new Image("/icons/" + name + ".png", iconSize, iconSize, true, true, true);
  }

  public final static Locale getSystemLocale () {
    String country = System.getProperty("user.country");
    return new Locale(String.format("%s_%s", country.toLowerCase(),country.toUpperCase()));
  }


}
