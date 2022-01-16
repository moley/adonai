package org.adonai;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.adonai.fx.UiUtils;
import org.adonai.model.Song;
import org.adonai.model.SongStructItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListOrganizer extends Application {

  private static final Logger LOGGER = LoggerFactory.getLogger(ListOrganizer.class);

  private static final String PREFIX =
    "http://icons.iconarchive.com/icons/jozef89/origami-birds/72/bird";

  private static final String SUFFIX =
    "-icon.png";
  Song song;




  @Override
  public void start(Stage stage) throws Exception {

    song = SongTestData.getSongWithTwoPartsTwoLines();


    ListView<SongStructItem> birdList = new ListView<SongStructItem>();
    birdList.setItems(FXCollections.observableArrayList(song.getStructItems()));
    birdList.setCellFactory(param -> new SongPartCell());
    birdList.setPrefWidth(180);


VBox layout = new VBox(birdList);
layout.setPadding(new Insets(10));

stage.setScene(new Scene(layout));
UiUtils.applyCss(stage.getScene());
stage.show();
}

public static void main(String[] args) {
launch(ListOrganizer.class);
}

private class SongPartCell extends ListCell<SongStructItem> {

public SongPartCell() {
ListCell thisCell = this;

setOnDragDetected(event -> {
if (getItem() == null) {
return;
}

Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
ClipboardContent content = new ClipboardContent();
content.putString(new Integer(getItem().hashCode()).toString());
dragboard.setContent(content);

event.consume();
});

setOnDragOver(event -> {
if (event.getGestureSource() != thisCell &&
event.getDragboard().hasString()) {
event.acceptTransferModes(TransferMode.MOVE);
}

event.consume();
});

setOnDragEntered(event -> {
if (event.getGestureSource() != thisCell &&
event.getDragboard().hasString()) {
setOpacity(0.3);
}
});

setOnDragExited(event -> {
if (event.getGestureSource() != thisCell &&
event.getDragboard().hasString()) {
setOpacity(1);
}
});

setOnDragDropped(event -> {
if (getItem() == null) {
return;
}

Dragboard db = event.getDragboard();
boolean success = false;

if (db.hasString()) {
ObservableList<SongStructItem> items = getListView().getItems();
int draggedIdx = -1;
for (int i = 0; i < items.size(); i++) {
if (new Integer(items.get(i).hashCode()).toString().equals(db.getString()))
draggedIdx = i;

}

int thisIdx = items.indexOf(getItem());

LOGGER.info("Vorher: " + song);

SongStructItem temp = song.getStructItems().get(draggedIdx);
song.getStructItems().set(draggedIdx, song.getStructItems().get(thisIdx));
song.getStructItems().set(thisIdx, temp);

items.set(draggedIdx, getItem());
items.set(thisIdx, temp);

LOGGER.info("Nachher: " + song.getStructItems());

getListView().setItems(FXCollections.observableArrayList(song.getStructItems()));

success = true;

}
event.setDropCompleted(success);

event.consume();
});

setOnDragDone(DragEvent::consume);
}

@Override
protected void updateItem(SongStructItem item, boolean empty) {

super.updateItem(item, empty);
if (empty || item == null) {
setGraphic(null);
setText(null);
} else {
setText(item.getPartId());
}

}
}


}