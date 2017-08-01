package org.adonai.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by OleyMa on 25.10.16.
 */
public class Consts {

  public final static int DEFAULT_LISTVIEW_WIDTH = 450;
  public final static int DEFAULT_WIDTH = 1400;
  public final static int DEFAULT_HEIGHT = 800;



  public final static ImageView createImageView (final String name) {
    return new ImageView(new Image("/icons/" + name + ".png", 30, 30, true, true, true));
  }

  public final static ImageView createImageViewLarge (final String name) {
    return new ImageView(new Image("/icons/" + name + ".png", 70, 70, true, true, true));
  }
}
