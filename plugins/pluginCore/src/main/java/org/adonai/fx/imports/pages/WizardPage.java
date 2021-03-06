package org.adonai.fx.imports.pages;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.adonai.ApplicationEnvironment;
import org.adonai.fx.imports.SongImportController;
import org.adonai.fx.imports.Wizard;

/**
 * basic wizard page class
 */
public abstract class WizardPage extends VBox {
  Button priorButton = new Button("_Previous");
  Button nextButton = new Button("N_ext");
  Button cancelButton = new Button("Cancel");
  Button finishButton = new Button("_Finish");

  protected SongImportController controller;

  protected ApplicationEnvironment applicationEnvironment;

  WizardPage(ApplicationEnvironment applicationEnvironment, String title, final SongImportController controller) {
    this.applicationEnvironment = applicationEnvironment;
    finishButton.setUserData("importsongwizard.btnFinish");
    priorButton.setUserData("importsongwizard.btnPrevious");
    nextButton.setUserData("importsongwizard.btnNext");
    cancelButton.setUserData("importsongwizard.btnCancel");
    this.controller = controller;
    Label label = new Label(title);
    label.setStyle("-fx-font-weight: bold; -fx-padding: 0 0 5 0;");
    setId(title);
    setSpacing(5);
    setStyle("-fx-padding:10; -fx-border-width: 3;");

    Parent pageContent = getContent();
    VBox.setVgrow(pageContent, Priority.ALWAYS);

    getChildren().addAll(pageContent, getButtons());

    priorButton.setOnAction(event -> priorPage());
    nextButton.setOnAction(event -> nextPage());
    cancelButton.setOnAction(event -> cancel());
    finishButton.setOnAction(event -> finish());
  }

  HBox getButtons() {
    Region spring = new Region();
    HBox.setHgrow(spring, Priority.ALWAYS);
    HBox buttonBar = new HBox(5);
    cancelButton.setCancelButton(true);
    finishButton.setDefaultButton(true);
    buttonBar.getChildren().addAll(spring, priorButton, nextButton, cancelButton, finishButton);
    return buttonBar;
  }

  abstract Parent getContent();

  boolean hasNextPage() {
    return getWizard().hasNextPage();
  }

  boolean hasPriorPage() {
    return getWizard().hasPriorPage();
  }

  void nextPage() {
    getWizard().nextPage();
  }

  void finish() {
    getWizard().finish();
  }

  void cancel () {
    getWizard().cancel();
  }

  void priorPage() {
    getWizard().priorPage();
  }

  void navTo(String id) {
    getWizard().navTo(id);
  }

  Wizard getWizard() {
    return (Wizard) getParent();
  }

  public void manageButtons() {
    if (!hasPriorPage()) {
      priorButton.setDisable(true);
    }

    if (!hasNextPage()) {
      nextButton.setDisable(true);
    }
  }
}
