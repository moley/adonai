package org.adonai.uitests;

import javafx.scene.Node;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyNodeMatchers {

  private static final Logger LOGGER = LoggerFactory.getLogger(MyNodeMatchers.class);


  public static Matcher<Node> withId (final String id) {
    return new BaseMatcher<Node>() {
      @Override
      public boolean matches(Object item) {
        if (item instanceof Node) {
          Node node = (Node) item;
          //for debugging
          // if (node.getId() != null)
          //  System.out.println (node.getId() + " - " + node.getClass().getSimpleName());
          return node.getId() != null && node.getId().equals(id);
        }
        return false;
      }

      @Override
      public void describeTo(Description description) {
        description.appendText("Node with id " + id);

      }
    };

  }

  public static Matcher<Node> withUserData (final String userData) {
    LOGGER.info("Find with userData " + userData);
    return new BaseMatcher<Node>() {
      @Override
      public boolean matches(Object item) {
        if (item instanceof Node) {
          Node node = (Node) item;
          //for debugging
          //LOGGER.info(node.getUserData() + " - " + node.getClass().getSimpleName());
          return node.getUserData() != null && node.getUserData().equals(userData);
        }
        return false;
      }

      @Override
      public void describeTo(Description description) {
        description.appendText("Node with userdata " + userData);

      }
    };

  }

}
