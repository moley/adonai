package org.adonai.fx.settings;

import java.util.Arrays;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.adonai.fx.Consts;
import org.adonai.model.Model;
import org.adonai.model.User;
import org.adonai.online.MailSender;
import org.controlsfx.control.Notifications;

public class SettingsUsersController extends AbstractSettingsController {



  @FXML
  private ListView<User> lviUsers;

  @FXML
  private TextField txtUsername;

  @FXML
  private TextField txtMailadress;

  @FXML
  private Button btnAddUser;

  @FXML
  private Button btnRemoveUser;

  @FXML
  private Button btnInvite;

  private void refresh () {
    this.lviUsers.setItems(FXCollections.observableArrayList(getConfiguration().getUsers()));
  }

  public void setModel (final Model model) {
    super.setModel(model);
    refresh();
    lviUsers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
      @Override
      public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
        select(oldValue, newValue);
      }
    });
    this.lviUsers.getSelectionModel().selectFirst();

    btnAddUser.setGraphic(Consts.createIcon("fas-plus", Consts.ICON_SIZE_VERY_SMALL));
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
    btnRemoveUser.setGraphic(Consts.createIcon("fas-minus", Consts.ICON_SIZE_VERY_SMALL));
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

    btnInvite.setText("Invite user");
    btnInvite.setOnAction(event -> {
      User selectedItem = lviUsers.getSelectionModel().getSelectedItem();
      if (selectedItem != null && selectedItem.getMail() != null && ! selectedItem.getMail().trim().isEmpty()) {
        MailSender mailSender = new MailSender();
        String accessKey = null; //TODO getApplicationEnvironment().getAdonaiProperties().getDropboxAccessToken() + "_" + getCurrentTenantModel().getTenant();
        List<String> text = Arrays.asList("Hello " + selectedItem.getUsername(),
                                          "",
                                          "You were invited to join adonai.",
                                          "To use it:",
                                          "  - Install and start the software from TODO",
                                          "  - Copy the access key '" + accessKey + "' to the first usage dialog",
                                          "",
                                          "Have fun using adonai.");
        mailSender.sendMail(Arrays.asList(selectedItem.getMail()), "Invitation to adonai", text);
      }
      else
        Notifications.create().title("Invite user").text("No valid mail address found. Please select user with an mail address.").show();
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
