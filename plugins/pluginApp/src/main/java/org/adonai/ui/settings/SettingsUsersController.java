package org.adonai.ui.settings;

import java.io.IOException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.adonai.model.User;
import org.adonai.ui.Consts;


public class SettingsUsersController extends AbstractSettingsController {

  @FXML
  ListView<User> lviUsers;

  @FXML
  TextField txtUsername;

  @FXML
  TextField txtMailadress;

  @FXML
  Button btnAddUser;

  @FXML
  Button btnRemoveUser;

  private void refresh () {
    this.lviUsers.setItems(FXCollections.observableArrayList(getConfiguration().getUsers()));
  }

  @FXML
  public void initialize() throws IOException {
    refresh();
    lviUsers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
      @Override
      public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
        select(oldValue, newValue);
      }
    });
    this.lviUsers.getSelectionModel().selectFirst();

    btnAddUser.setGraphic(Consts.createIcon("fa-plus", Consts.ICON_SIZE_VERY_SMALL));
    btnAddUser.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        User newUser = new User();
        newUser.setUsername("new");
        getConfiguration().getUsers().add(newUser);
        refresh();
        lviUsers.requestFocus();
        lviUsers.getSelectionModel().select(newUser);
        txtUsername.requestFocus();

      }
    });
    btnRemoveUser.setGraphic(Consts.createIcon("fa-minus", Consts.ICON_SIZE_VERY_SMALL));
    btnRemoveUser.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        int toSelect = Math.max(0, lviUsers.getSelectionModel().getSelectedIndex() - 1);
        getConfiguration().getUsers().remove(lviUsers.getSelectionModel().getSelectedItem());
        refresh();
        if (! lviUsers.getItems().isEmpty()) {
          lviUsers.requestFocus();
          lviUsers.getSelectionModel().select(toSelect);
        }
      }
    });

  }

  public void select (User oldUser, User user) {
    if (oldUser != null) {
      this.txtUsername.textProperty().unbindBidirectional(oldUser.usernameProperty());
      this.txtMailadress.textProperty().unbindBidirectional(oldUser.mailProperty());
    }

    if (user != null) {
      this.txtUsername.textProperty().bindBidirectional(user.usernameProperty());
      this.txtMailadress.textProperty().bindBidirectional(user.mailProperty());
    }
  }
}