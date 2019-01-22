package org.adonai.ui.imports;

import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.adonai.ui.AbstractPage;
import org.testfx.framework.junit.ApplicationTest;

public class ImportSongWizardPage extends AbstractPage {

  private ApplicationTest applicationTest;


  public ImportSongWizardPage(final ApplicationTest applicationTest) {
    this.applicationTest = applicationTest;
  }


  public void chooseFromNewSong () {
    applicationTest.clickOn(getRbFromNewSong());
  }

  public void chooseFromClipboard () {
    applicationTest.clickOn(getRbFromClipboard());
  }

  public void next () {
    applicationTest.clickOn(getBtnNext());
  }

  public void finish () {
    applicationTest.clickOn(getBtnFinish());
  }

  private TextField getTxtTitle () { return applicationTest.lookup(nodeWithId("txtTitle")).query(); }


  private RadioButton getRbFromClipboard () {
    return applicationTest.lookup(nodeWithId("rbFromTextFile")).query();
  }
  private RadioButton getRbFromNewSong() {
    return applicationTest.lookup(nodeWithId("rbNewSong")).query();
  }


  private Node getBtnPrevious () {
    return applicationTest.lookup(nodeWithId("btnPrevious")).query();
  }
  private Node getBtnNext () {
    return applicationTest.lookup(nodeWithId("btnNext")).query();
  }
  private Node getBtnCancel () {
    return applicationTest.lookup(nodeWithId("btnCancel")).query();
  }
  private Node getBtnFinish () {
    return applicationTest.lookup(nodeWithId("btnFinish")).query();
  }

  public void newSong (final String title) {
    chooseFromNewSong();
    next();
    getTxtTitle().setText(title);
    next();
    finish();
  }


}
