package org.adonai.ui;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import org.hamcrest.Matcher;
import org.testfx.framework.junit.ApplicationTest;

public class AbstractPage<T> {

  protected Matcher<Node> nodeWithId (final String id) {
    return MyNodeMatchers.withId(id);
  }

  protected Matcher<Node> nodeWithUserData (final String userdata) {
    return MyNodeMatchers.withUserData(userdata);
  }

  public void pressAndRelease (ApplicationTest applicationTest, KeyCode keyCode) {
    applicationTest.press(keyCode).release(keyCode);
  }





}
