package org.adonai.uitests.pages;

import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.testfx.framework.junit.ApplicationTest;

public class ImportSongWizardPage extends AbstractPage {

  public ImportSongWizardPage(final ApplicationTest applicationTest) {
    super (applicationTest);
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

  private TextField getTxtTitle () { return applicationTest.lookup(nodeWithUserData("importsongwizard.txtTitle")).query(); }


  private RadioButton getRbFromClipboard () {
    return applicationTest.lookup(nodeWithUserData("importsongwizard.rbFromTextFile")).query();
  }
  private RadioButton getRbFromNewSong() {
    return applicationTest.lookup(nodeWithUserData("importsongwizard.rbNewSong")).query();
  }


  private Node getBtnPrevious () {
    return applicationTest.lookup(nodeWithUserData("importsongwizard.btnPrevious")).query();
  }
  private Node getBtnNext () {
    return applicationTest.lookup(nodeWithUserData("importsongwizard.btnNext")).query();
  }
  private Node getBtnCancel () {
    return applicationTest.lookup(nodeWithUserData("importsongwizard.btnCancel")).query();
  }
  private Node getBtnFinish () {
    return applicationTest.lookup(nodeWithUserData("importsongwizard.btnFinish")).query();
  }

  public void newSong (final String title) {
    chooseFromNewSong();
    next();
    getTxtTitle().setText(title);
    next();
    finish();
  }


}
