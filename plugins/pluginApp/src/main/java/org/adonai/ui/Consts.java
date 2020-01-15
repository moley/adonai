package org.adonai.ui;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.kordamp.ikonli.javafx.FontIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by OleyMa on 25.10.16.
 */
public class Consts {

  private static final Logger LOGGER = LoggerFactory.getLogger(Consts.class);

  public final static int ICON_SIZE_TOOLBAR = 15;
  public final static int ICON_SIZE_VERY_SMALL = 20;
  public final static int ICON_SIZE_SMALL = 30;
  public final static int ICON_SIZE_MEDIUM = 50;
  public final static int ICON_SIZE_LARGE = 70;

  public final static int DEFAULT_LISTVIEW_WIDTH = 450;



  private static int defaultWidth = 1400;
  private static int defaultHeight = 800;


  public final static String ADONAI_HOME_PROP = "adonai.home";

  private static HashMap<ImageKey, Image> imagesCache = new HashMap<ImageKey, Image>();

  public final static File getUserHome () {
    return new File (System.getProperty(ADONAI_HOME_PROP) != null ? System.getProperty(ADONAI_HOME_PROP) : System.getProperty("user.home"));
  }

  public final static File getAdonaiHome () {
    return new File (getUserHome(), ".adonai");
  }

  public final static File getAdditionalsPath () {
    return new File (getAdonaiHome(), "additionals");
  }


  public final static ImageView createImageView (final String name, int iconSize) {
    ImageView imageView = getOrLoadImage(new ImageKey(name, iconSize));
    return imageView;
  }

  public final static FontIcon createIcon (String name, int iconSize) {
    FontIcon fontIcon =  new FontIcon(name);
    fontIcon.setIconSize(iconSize);
    return fontIcon;
  }

  public final static Image createImage (String name, int iconSize) {
    ImageView imageView = getOrLoadImage(new ImageKey(name, iconSize));
    return imageView.getImage();
  }

  public final static ImageView getOrLoadImage (final ImageKey imageKey) {
    Image cachedImage = imagesCache.get(imageKey);
    if (cachedImage == null) {
      if (LOGGER.isDebugEnabled())
        LOGGER.debug("create image " + imageKey.getName());
      InputStream inputStream = Consts.class.getResourceAsStream("/icons/" + imageKey.getName() + ".png");
      if (inputStream == null)
        throw new IllegalStateException("Could not load image " + imageKey.getName() + " with classloader " + Consts.class.getClassLoader());

      cachedImage = new Image(inputStream, imageKey.getIconSize(), imageKey.getIconSize(), true, true);
      imagesCache.put(imageKey, cachedImage);
    }
    else {
      if (LOGGER.isDebugEnabled())
        LOGGER.debug("load image " + imageKey.getName() + " from cache");
    }

    return new ImageView(cachedImage);

  }

  public static int getDefaultWidth() {
    return defaultWidth;
  }

  public static int getDefaultHeight() {
    return defaultHeight;
  }

  public static void setDefaultWidth(int defaultWidth) {
    Consts.defaultWidth = defaultWidth;
  }

  public static void setDefaultHeight(int defaultHeight) {
    Consts.defaultHeight = defaultHeight;
  }



}
