package org.adonai.plugin.core.fxml.action;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.adonai.ApplicationEnvironment;
import org.adonai.api.MainAction;
import org.adonai.model.Model;
import org.controlsfx.control.Notifications;

@Slf4j
public class SaveAction implements MainAction {

  @Override public String getIconname() {
    return "fas-save";
  }

  @Override public String getDisplayName() {
    return "Save";
  }

  @Override public EventHandler<ActionEvent> getEventHandler(ApplicationEnvironment applicationEnvironment) {
    return new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        try {
          Model model = applicationEnvironment.getModel();
          model.save();
          Notifications.create().text("Model saved successfully (" + model.getCurrentTenantModel().getConfigFile().getAbsolutePath() + ")").showInformation();
        } catch (Exception e) {
          log.error(e.getLocalizedMessage(), e);
          Notifications.create().text("Error saving model: " + e.getLocalizedMessage()).showError();
        }
      }
    };
  }
}
