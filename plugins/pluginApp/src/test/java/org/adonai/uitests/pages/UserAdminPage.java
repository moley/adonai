package org.adonai.uitests.pages;

import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.adonai.model.User;
import org.adonai.uitests.MyNodeMatchers;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.service.query.NodeQuery;

public class UserAdminPage extends AbstractPage {

  public UserAdminPage (ApplicationTest applicationTest) {
    super (applicationTest);
  }

  private ListView<User> getLviUsers () {
    NodeQuery nodeQuery = applicationTest.lookup(MyNodeMatchers.withUserData("users.lviUsers"));
    return nodeQuery.query();
  }

  private Button getBtnAdd () {
    NodeQuery nodeQuery = applicationTest.lookup(MyNodeMatchers.withUserData("users.btnAdd"));
    return nodeQuery.query();
  }

  public void add () {
    applicationTest.clickOn(getBtnAdd());
  }

  public void remove () {
    applicationTest.clickOn(getBtnRemove());
  }

  public List<User> getUsers () {
    return getLviUsers().getItems();
  }

  public String getCurrentUsername () {
    return getTxtUsername().getText();
  }

  private Button getBtnRemove () {
    NodeQuery nodeQuery = applicationTest.lookup(MyNodeMatchers.withUserData("users.btnRemove"));
    return nodeQuery.query();
  }

  private TextField getTxtUsername () {
    NodeQuery nodeQuery = applicationTest.lookup(MyNodeMatchers.withUserData("users.txtUsername"));
    return nodeQuery.query();
  }
}
