package org.adonai.uitests.pages;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.adonai.uitests.MyNodeMatchers;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.service.query.NodeQuery;

public class SongPartDetailsPage extends AbstractPage {

  public SongPartDetailsPage(ApplicationTest applicationTest) {
    super (applicationTest);
  }

  private ComboBox getCmbCopiedPart () {
    NodeQuery nodeQuery = applicationTest.lookup(MyNodeMatchers.withUserData("songpartdetails.cboCopyExisting"));
    return nodeQuery.query();
  }

  private ComboBox getCmbPartType () {
    NodeQuery nodeQuery = applicationTest.lookup(MyNodeMatchers.withUserData("songpartdetails.cboSongPartType"));
    return nodeQuery.query();
  }

  private TextField getTxtQuantity () {
    NodeQuery nodeQuery = applicationTest.lookup(MyNodeMatchers.withUserData("songpartdetails.txtQuantity"));
    return nodeQuery.query();
  }

  private TextField getTxtRemarks () {
    NodeQuery nodeQuery = applicationTest.lookup(MyNodeMatchers.withUserData("songpartdetails.txtRemarks"));
    return nodeQuery.query();
  }

  public String getQuantity () {
    return getTxtQuantity().getText();
  }

  public void setQuantity (final String newTitle) {getTxtQuantity().setText(newTitle);}

}
