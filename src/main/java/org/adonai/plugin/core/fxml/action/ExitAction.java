package org.adonai.plugin.core.fxml.action;

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;
import org.adonai.ApplicationEnvironment;
import org.adonai.api.MainAction;
import org.adonai.fx.UiUtils;
import org.adonai.model.Model;

public class ExitAction implements MainAction {
  @Override public String getIconname() {
    return "fas-power-off";
  }

  @Override public String getDisplayName() {
    return "Exit adonai";
  }

  @Override public EventHandler<ActionEvent> getEventHandler(ApplicationEnvironment applicationEnvironment) {
    return new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        Model model = applicationEnvironment.getModel();

        if (model.hasChanged()) {
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
          alert.setTitle("Exit adonai");
          alert.setHeaderText("You have unsaved changes!");
          alert.setContentText("Do you want to save your changes?");
          DialogPane dialogPane = alert.getDialogPane();
          alert.initStyle(StageStyle.UNDECORATED);
          dialogPane.setMinWidth(600);
          dialogPane.setMinHeight(200);
          UiUtils.applyCss(dialogPane.getScene());
          dialogPane.getStyleClass().add("myDialog");


          Optional<ButtonType> result = alert.showAndWait();
          if (result.get() == ButtonType.OK) {
            model.save();
          }
        }

        System.exit(0);

      }
    };
  }
}
