package org.adonai.fx;

import javafx.stage.Stage;
import org.adonai.ApplicationEnvironment;

public class AbstractController {

  private ApplicationEnvironment applicationEnvironment;

  private Stage stage;


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

}
