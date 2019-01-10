package org.adonai.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.adonai.StringUtils;
import org.adonai.actions.openAdditionals.OpenAdditionalHandler;
import org.adonai.additionals.AdditionalsActionHandler;
import org.adonai.model.Additional;
import org.adonai.model.AdditionalType;
import org.adonai.model.Song;

import java.util.logging.Logger;

public class SongCellFactory implements Callback<ListView<Song>, ListCell<Song>> {

  private AdditionalsActionHandler additionalsActionHandler = new AdditionalsActionHandler();

  private static final Logger LOGGER = Logger.getLogger(SongCellFactory.class.getName());


  @Override
  public ListCell<Song> call(ListView<Song> param) {
    final ListCell<Song> cell = new ListCell<Song>() {

      @Override
      protected void updateItem(Song item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null) {
          HBox hbox = new HBox();
          Label label = new Label(String.format("%3d - %s", item.getId(), item.getTitle()));
          label.setMinWidth(800);
          label.setMinHeight(40);

          hbox.getChildren().add(label);
          System.out.println (item + "-" + empty);

          for (AdditionalType nextType: AdditionalType.values()) {

              String additionalName = nextType.name().toLowerCase();
              String additionalDisplay = StringUtils.getFirstUpper(additionalName);
              boolean additionalExists = item.findAdditional(nextType) != null;
              String iconName = (additionalExists ? additionalName: additionalName + "_disabled");
              LOGGER.info("Icon " + iconName + " for additional " + additionalName + " on song " + item.getId() + "(" + additionalExists + ")");

              ImageView image = Consts.createImageView(iconName, Consts.ICON_SIZE_SMALL);
              Button btnStatus = new Button("", image);
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
        else
          setGraphic(null);

      }
    };
    return cell;
  }

}
