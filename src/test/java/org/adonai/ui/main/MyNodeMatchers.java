package org.adonai.ui.main;

import javafx.scene.Node;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class MyNodeMatchers {

  public static Matcher<Node> withId (final String id) {
    return new BaseMatcher<Node>() {
      @Override
      public boolean matches(Object item) {
        if (item instanceof Node) {
          Node node = (Node) item;
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

}
