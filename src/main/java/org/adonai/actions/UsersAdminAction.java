package org.adonai.actions;

import org.adonai.model.Configuration;
import org.adonai.ui.Mask;
import org.adonai.ui.MaskLoader;
import org.adonai.ui.users.UsersController;

public class UsersAdminAction {

  public final static int WIDTH = 800;
  public final static int HEIGHT = 400;

  public void open (Configuration configuration, Double x, Double y) {
    MaskLoader<UsersController> maskLoader = new MaskLoader();
    Mask<UsersController> searchMask = maskLoader.load("users");
    searchMask.setSize(WIDTH, HEIGHT);
    searchMask.setPosition(x, y);
    searchMask.getController().setConfiguration(configuration);
    searchMask.show();

  }
}
