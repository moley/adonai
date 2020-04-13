package org.adonai.ui;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.adonai.model.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionCellFactory implements Callback<ListView<Session>, ListCell<Session>> {


  private static final Logger LOGGER = LoggerFactory.getLogger(SessionCellFactory.class);


  @Override
  public ListCell<Session> call(ListView<Session> param) {
    final ListCell<Session> cell = new ListCell<Session>() {

      @Override
      protected void updateItem(Session item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null) {
          setText(item.getName().toUpperCase());
          setGraphic(null);
        }
        else {
          setText(null);
          setGraphic(null);
        }

      }
    };
    return cell;
  }

}
