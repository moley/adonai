package org.adonai.uitests.pages;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import org.adonai.uitests.MyNodeMatchers;
import org.hamcrest.Matcher;
import org.testfx.framework.junit.ApplicationTest;

public class AbstractPage<T> {

  protected ApplicationTest applicationTest;

  public AbstractPage (ApplicationTest applicationTest) {
    this.applicationTest = applicationTest;
  }


  protected Matcher<Node> nodeWithId (final String id) {
    return MyNodeMatchers.withId(id);
  }

  protected Matcher<Node> nodeWithUserData (final String userdata) {
    return MyNodeMatchers.withUserData(userdata);
  }

  public void pressAndRelease (KeyCode keyCode) {
    applicationTest.press(keyCode).release(keyCode);
  }

  public ApplicationTest getApplicationTest () {
    return applicationTest;
  }






}
