package org.adonai.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.adonai.StringUtils;
import org.adonai.model.AdditionalType;
import org.adonai.model.Song;
import org.kordamp.ikonli.javafx.FontIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SongCellFactory implements Callback<ListView<Song>, ListCell<Song>> {


  private static final Logger LOGGER = LoggerFactory.getLogger(SongCellFactory.class);


  @Override
  public ListCell<Song> call(ListView<Song> param) {
    final ListCell<Song> cell = new ListCell<Song>() {

      @Override
      protected void updateItem(Song item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null) {
          HBox hbox = new HBox();
          hbox.setAlignment(Pos.CENTER_LEFT);
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

          }

          Label lblSpeed = new Label();
          lblSpeed.setMinWidth(100);
          lblSpeed.setText(item.getSpeed() != null ? item.getSpeed().toString(): "");
          hbox.getChildren().add(lblSpeed);

          Label lblKey = new Label();
          lblKey.setMinWidth(50);
          lblKey.setText(item.getCurrentKey() != null ? item.getCurrentKey().toString(): "");
          hbox.getChildren().add(lblKey);

          Label lblKeyOrigin = new Label();
          lblKeyOrigin.setMinWidth(50);
          lblKeyOrigin.setText(item.getOriginalKey() != null ? ("(" + item.getOriginalKey().toString() + ")"): "");
          hbox.getChildren().add(lblKeyOrigin);

          Label lblLeadVoice = new Label();
          lblLeadVoice.setMinWidth(50);
          lblLeadVoice.setText(item.getLeadVoice() != null ? item.getLeadVoice().getUsername(): "");
          hbox.getChildren().add(lblLeadVoice);

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
