package org.adonai.fx;

import javafx.stage.Stage;
import org.adonai.ApplicationEnvironment;
import org.adonai.fx.main.MainController;

public class AbstractController {

  private ApplicationEnvironment applicationEnvironment;

  private Stage stage;

  private MainController mainController;


  public ApplicationEnvironment getApplicationEnvironment() {
    return applicationEnvironment;
  }

  public void setApplicationEnvironment(ApplicationEnvironment applicationEnvironment) {
    this.applicationEnvironment = applicationEnvironment;
  }

  public Stage getStage() {
    return stage;
  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }

  public MainController getMainController() {
    return mainController;
  }

  public void setMainController(MainController mainController) {
    this.mainController = mainController;
  }
}
