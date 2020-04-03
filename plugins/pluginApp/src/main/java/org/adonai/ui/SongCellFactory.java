package org.adonai.ui;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.adonai.StringUtils;
import org.adonai.model.AdditionalType;
import org.adonai.model.Session;
import org.adonai.model.Song;
import org.kordamp.ikonli.javafx.FontIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SongCellFactory implements Callback<ListView<Song>, ListCell<Song>> {

  private static final Logger LOGGER = LoggerFactory.getLogger(SongCellFactory.class);

  private Session session = null;

  @Override public ListCell<Song> call(ListView<Song> param) {
    final ListCell<Song> cell = new ListCell<Song>() {

      @Override protected void updateItem(Song item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null) {
          HBox hbox = new HBox();
          hbox.setAlignment(Pos.CENTER_LEFT);
          String labelText = item.getId() != null ?
              String.format("%3d - %s", item.getId(), item.getTitle()) :
              item.getTitle();
          Label label = new Label(labelText);
          label.setMinWidth(800);
          label.setMinHeight(40);

          hbox.getChildren().add(label);

          for (AdditionalType nextType : AdditionalType.values()) {

            String additionalName = nextType.name().toLowerCase();
            String additionalDisplay = StringUtils.getFirstUpper(additionalName);
            boolean additionalExists = item.findAdditional(nextType) != null;
            String icon = nextType.getIconName();
            FontIcon fontIcon = Consts.createIcon(icon, Consts.ICON_SIZE_VERY_SMALL);
            Button btnStatus = new Button("", fontIcon);
            btnStatus.setId("statusbutton");
            btnStatus.setStyle(additionalExists ? "-fx-background-color: rgb(128, 186, 144);": "    -fx-background-color: transparent;");
            Tooltip tooltip = new Tooltip();
            tooltip.setText(additionalDisplay);
            btnStatus.setTooltip(tooltip);
            hbox.getChildren().add(btnStatus);

          }

          Label lblSpeed = new Label();
          lblSpeed.setMinWidth(100);
          lblSpeed.setText(item.getSpeed() != null ? item.getSpeed().toString() : "");
          hbox.getChildren().add(lblSpeed);

          Label lblKey = new Label();
          lblKey.setMinWidth(50);
          lblKey.setText(item.getCurrentKey() != null ? item.getCurrentKey().toString() : "");
          hbox.getChildren().add(lblKey);

          Label lblKeyOrigin = new Label();
          lblKeyOrigin.setMinWidth(50);
          lblKeyOrigin.setText(item.getOriginalKey() != null ? ("(" + item.getOriginalKey().toString() + ")") : "");
          hbox.getChildren().add(lblKeyOrigin);

          Label lblLeadVoice = new Label();
          lblLeadVoice.setMinWidth(50);
          lblLeadVoice.setText(item.getLeadVoice() != null ? item.getLeadVoice().getUsername() : "");
          hbox.getChildren().add(lblLeadVoice);

          setGraphic(hbox);
        } else {
          setText(null);
          setGraphic(null);
        }

      }
    };

    cell.setOnDragDetected(event -> {
      if (cell.getItem() == null) {
        return;
      }

      Dragboard dragboard = cell.startDragAndDrop(TransferMode.MOVE);
      ClipboardContent content = new ClipboardContent();
      content.putString(new Integer(cell.getItem().hashCode()).toString());
      dragboard.setContent(content);

      event.consume();
    });

    cell.setOnDragOver(event -> {
      if (event.getGestureSource() != cell && event.getDragboard().hasString()) {
        event.acceptTransferModes(TransferMode.MOVE);
      }

      event.consume();
    });

    cell.setOnDragEntered(event -> {
      if (event.getGestureSource() != cell && event.getDragboard().hasString()) {
        cell.setOpacity(0.3);
      }
    });

    cell.setOnDragExited(event -> {
      if (event.getGestureSource() != cell && event.getDragboard().hasString()) {
        cell.setOpacity(1);
      }
    });

    cell.setOnDragDropped(event -> {
      if (cell.getItem() == null) {
        return;
      }

      Dragboard db = event.getDragboard();
      boolean success = false;

      if (db.hasString()) {
        ObservableList<Song> items = cell.getListView().getItems();
        int draggedIdx = -1;
        for (int i = 0; i < items.size(); i++) {
          if (new Integer(items.get(i).hashCode()).toString().equals(db.getString()))
            draggedIdx = i;

        }

        int thisIdx = items.indexOf(cell.getItem());

        Song temp = items.get(draggedIdx);
        session.getSongs().set(draggedIdx, cell.getItem().getId());
        session.getSongs().set(thisIdx, temp.getId());

        LOGGER.info("New Session: " + session);

        items.set(draggedIdx, cell.getItem());
        items.set(thisIdx, temp);

        cell.getListView().setItems(FXCollections.observableArrayList(items));

        success = true;

      } event.setDropCompleted(success);

      event.consume();
    });

    cell.setOnDragDone(DragEvent::consume);

    return cell;
  }

  public Session getSession() {
    return session;
  }

  public void setSession(Session session) {
    this.session = session;
  }
}
