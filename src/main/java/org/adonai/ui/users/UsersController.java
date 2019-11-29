package org.adonai.ui.users;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.adonai.model.Configuration;
import org.adonai.model.User;
import org.adonai.ui.Consts;


public class UsersController {

  @FXML
  ListView<User> lviUsers;

  @FXML
  TextField txtUsername;

  @FXML
  Button btnAddUser;

  @FXML
  Button btnRemoveUser;

  private Configuration configuration;

  private void refresh () {
    this.lviUsers.setItems(FXCollections.observableArrayList(configuration.getUsers()));
  }

  public void setConfiguration(Configuration configuration) {
    this.configuration = configuration;
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
        configuration.getUsers().add(newUser);
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
        configuration.getUsers().remove(lviUsers.getSelectionModel().getSelectedItem());
        refresh();
        if (! lviUsers.getItems().isEmpty()) {
          lviUsers.requestFocus();
          lviUsers.getSelectionModel().select(toSelect);
        }
      }
    });

  }

  public void select (User oldUser, User user) {
    if (oldUser != null)
      this.txtUsername.textProperty().unbindBidirectional(oldUser.usernameProperty());

    if (user != null)
      this.txtUsername.textProperty().bindBidirectional(user.usernameProperty());
  }
}
