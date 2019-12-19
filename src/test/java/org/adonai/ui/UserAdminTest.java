package org.adonai.ui;

import javafx.stage.Stage;
import org.adonai.AbstractAdonaiUiTest;
import org.adonai.testdata.TestDataCreator;
import org.adonai.ui.pages.MainMaskPage;
import org.adonai.ui.pages.UserAdminPage;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserAdminTest extends AbstractAdonaiUiTest {

  private UserAdminPage userAdminPage;

  private MainMaskPage mainMaskPage;


  @BeforeClass
  public static void beforeClass () {
    TestUtil.initialize();
  }


  @Override
  public void start(Stage stage) throws Exception {
    TestDataCreator testDataCreator = new TestDataCreator();
    testDataCreator.createTestData(TestUtil.getDefaultTestDataPath(), false);
    super.start(stage);
    mainMaskPage = new MainMaskPage( this);
    mainMaskPage.openStage();
  }

  @Test
  public void addUser () {
    userAdminPage = mainMaskPage.stepToUserAdmin();
    int numberOfUsers = userAdminPage.getUsers().size();
    userAdminPage.add();
    int numberOfUsersAfterAdd = userAdminPage.getUsers().size();
    Assert.assertEquals ("Number of users not added", numberOfUsersAfterAdd, numberOfUsers + 1);
    Assert.assertEquals ("new", userAdminPage.getCurrentUsername());
  }

  @Test
  public void removeUser () {
    userAdminPage = mainMaskPage.stepToUserAdmin();
    int numberOfUsers = userAdminPage.getUsers().size();
    userAdminPage.remove();
    int numberOfUsersAfterAdd = userAdminPage.getUsers().size();
    Assert.assertEquals ("Number of users not added", numberOfUsersAfterAdd, numberOfUsers -1);
    Assert.assertEquals (userAdminPage.getUsers().get(0).getUsername(), userAdminPage.getCurrentUsername());

  }


}
