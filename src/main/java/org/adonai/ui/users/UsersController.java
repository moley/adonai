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
        if (newValue != null) {
          select(newValue);
        }
      }
    });
    this.lviUsers.getSelectionModel().selectFirst();

    btnAddUser.setGraphic(Consts.createImageView("plus", Consts.ICON_SIZE_SMALL));
    btnAddUser.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        User newUser = new User();
        newUser.setUsername("new");
        configuration.getUsers().add(newUser);
        refresh();
        lviUsers.requestFocus();
        lviUsers.getSelectionModel().select(newUser);

      }
    });
    btnRemoveUser.setGraphic(Consts.createImageView("minus", Consts.ICON_SIZE_SMALL));
    btnRemoveUser.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        configuration.getUsers().remove(lviUsers.getSelectionModel().getSelectedItem());
        refresh();
      }
    });

  }

  public void select (User user) {
    this.txtUsername.textProperty().bindBidirectional(user.usernameProperty());
  }
}
