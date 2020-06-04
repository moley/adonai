package org.adonai.plugin.core.fxml.action;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.adonai.ApplicationEnvironment;
import org.adonai.api.MainAction;
import org.adonai.model.Model;
import org.adonai.plugin.core.CorePlugin;
import org.controlsfx.control.Notifications;
import org.pf4j.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Extension(ordinal=90)
public class SaveAction implements MainAction {

  private static final Logger logger = LoggerFactory.getLogger(CorePlugin.class);


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
          Notifications.create().text("Model saved successfully").showInformation();
        } catch (Exception e) {
          logger.error(e.getLocalizedMessage(), e);
          Notifications.create().text("Error saving model: " + e.getLocalizedMessage()).showError();
        }
      }
    };
  }
}
