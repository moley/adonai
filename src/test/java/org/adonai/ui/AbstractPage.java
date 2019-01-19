package org.adonai.ui;

import javafx.scene.Node;
import org.adonai.ui.main.MyNodeMatchers;
import org.hamcrest.Matcher;

public class AbstractPage<T> {

  protected Matcher<Node> nodeWithId (final String id) {
    return MyNodeMatchers.withId(id);
  }


}
