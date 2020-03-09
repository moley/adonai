package org.adonai.uitests.pages;

import javafx.scene.control.TextField;
import org.adonai.uitests.MyNodeMatchers;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.service.query.NodeQuery;

public class SongDetailsPage extends AbstractPage {

  public SongDetailsPage (ApplicationTest applicationTest) {
    super (applicationTest);
  }

  private TextField getTxtTitle () {
    NodeQuery nodeQuery = applicationTest.lookup(MyNodeMatchers.withUserData("songdetails.txtTitle"));
    return nodeQuery.query();
  }

  public String getTitle () {
    return getTxtTitle().getText();
  }

  public void setTitle (final String newTitle) {getTxtTitle().setText(newTitle);}

}
