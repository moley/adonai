package org.adonai.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.adonai.StringUtils;
import org.adonai.actions.openAdditionals.OpenAdditionalHandler;
import org.adonai.additionals.AdditionalsActionHandler;
import org.adonai.model.AdditionalType;
import org.adonai.model.Song;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SongCellFactory implements Callback<ListView<Song>, ListCell<Song>> {

  private AdditionalsActionHandler additionalsActionHandler = new AdditionalsActionHandler();

  private static final Logger LOGGER = LoggerFactory.getLogger(SongCellFactory.class);


  @Override
  public ListCell<Song> call(ListView<Song> param) {
    final ListCell<Song> cell = new ListCell<Song>() {

      @Override
      protected void updateItem(Song item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null) {
          HBox hbox = new HBox();
          String labelText = item.getId() != null ? String.format("%3d - %s", item.getId(), item.getTitle()) : item.getTitle();
          Label label = new Label(labelText);
          label.setMinWidth(800);
          label.setMinHeight(40);

          hbox.getChildren().add(label);

          for (AdditionalType nextType: AdditionalType.values()) {

              String additionalName = nextType.name().toLowerCase();
              String additionalDisplay = StringUtils.getFirstUpper(additionalName);
              boolean additionalExists = item.findAdditional(nextType) != null;
              String iconName = (additionalExists ? additionalName: additionalName + "_disabled");
              if (LOGGER.isDebugEnabled())
                LOGGER.debug("Icon " + iconName + " for additional " + additionalName + " on song " + item.getId() + "(" + additionalExists + ")");

              ImageView image = Consts.createImageView(iconName, Consts.ICON_SIZE_VERY_SMALL);
              Button btnStatus = new Button("", image);
              btnStatus.setId("statusbutton");
              Tooltip tooltip = new Tooltip();
              tooltip.setText(additionalDisplay);
              btnStatus.setTooltip(tooltip);
              hbox.getChildren().add(btnStatus);
              if (additionalExists) {
                btnStatus.setOnAction(new EventHandler<ActionEvent>() {
                  @Override
                  public void handle(ActionEvent event) {

                    OpenAdditionalHandler openAdditionalHandler = additionalsActionHandler.createOpenAdditionalHandler(nextType);
                    openAdditionalHandler.open(item);
                  }
                });
              }

          }
          setGraphic(hbox);
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
