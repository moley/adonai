package org.adonai.fx.additionals;

import java.util.HashMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import org.adonai.fx.AbstractController;
import org.adonai.fx.Consts;
import org.adonai.fx.Mask;
import org.adonai.fx.MaskLoader;
import org.adonai.fx.renderer.AdditionalCellRenderer;
import org.adonai.model.Additional;
import org.adonai.model.AdditionalType;
import org.adonai.model.WithAdditionals;

public class AdditionalsController extends AbstractController {

  @FXML private StackPane panDetails;
  @FXML private MenuButton btnAdd;
  @FXML private Button btnRemove;
  @FXML private ListView<Additional> lviAdditionals;

  private HashMap<AdditionalType, Mask<AdditionalDetailController>> masks = new HashMap<AdditionalType, Mask<AdditionalDetailController>>();

  private WithAdditionals withAdditionals;

  public void initialize() {
    btnRemove.setGraphic(Consts.createIcon("fas-trash", Consts.ICON_SIZE_VERY_SMALL));
    btnRemove.setOnAction(action -> removeAdditional());
    btnRemove.setTooltip(new Tooltip("Remove selected part"));
    btnAdd.setGraphic(Consts.createIcon("fas-plus", Consts.ICON_SIZE_VERY_SMALL));
    btnAdd.setTooltip(new Tooltip("Add new part after selected part"));
    for (AdditionalType nextType : AdditionalType.values()) {
      if (nextType.isVisible()) {
        MenuItem menuItem = new MenuItem(nextType.name(), Consts.createIcon(nextType.getIconName(), Consts.ICON_SIZE_TOOLBAR));
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
          @Override public void handle(ActionEvent event) {
            final AdditionalType currentAdditionalType = nextType;
            addAdditional(currentAdditionalType);
          }
        });
        btnAdd.getItems().add(menuItem);
      }
    }

    lviAdditionals.setCellFactory(cellfactory -> new AdditionalCellRenderer());
    lviAdditionals.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Additional>() {
      @Override public void changed(ObservableValue<? extends Additional> observable, Additional oldValue,
          Additional newValue) {
        if (oldValue != null) {
          AdditionalType additionalType = oldValue.getAdditionalType();
          Mask<AdditionalDetailController> additionalDetailControllerMask = masks.get(additionalType);
          additionalDetailControllerMask.getController().save();
        }
        setDetails(newValue);

      }
    });

    for (AdditionalType nextType : AdditionalType.values()) {
      MaskLoader<AdditionalDetailController> songdetailsMaskLoader = new MaskLoader<AdditionalDetailController>();
      Mask<AdditionalDetailController> songdetailsMask = songdetailsMaskLoader
          .load("additional_" + nextType.name().toLowerCase());
      songdetailsMask.getRoot().setVisible(false);
      panDetails.getChildren().add(songdetailsMask.getRoot());
      masks.put(nextType, songdetailsMask);
    }

  }

  public void save() {
    Additional selectedItem = lviAdditionals.getSelectionModel().getSelectedItem();
    if (selectedItem != null) {
      Mask<AdditionalDetailController> additionalDetailControllerMask = masks.get(selectedItem.getAdditionalType());
      additionalDetailControllerMask.getController().save();
    }
  }

  private void addAdditional(AdditionalType currentAdditionalType) {
    Additional newAdditional = new Additional();
    newAdditional.setAdditionalType(currentAdditionalType);
    withAdditionals.getAdditionals().add(newAdditional);
    setWithAdditionals(withAdditionals);
  }

  private void removeAdditional() {
    Additional additional = lviAdditionals.getSelectionModel().getSelectedItem();
    withAdditionals.getAdditionals().remove(additional);
    setWithAdditionals(withAdditionals);
  }

  public WithAdditionals getWithAdditionals() {
    return withAdditionals;
  }

  public void setWithAdditionals(WithAdditionals withAdditionals) {
    this.withAdditionals = withAdditionals;
    lviAdditionals.setItems(FXCollections.observableArrayList(withAdditionals.getAdditionals()));
    if (lviAdditionals.getSelectionModel().getSelectedItem() == null)
      lviAdditionals.getSelectionModel().selectFirst();
  }

  public void setDetails(Additional additional) {
    for (Node next : panDetails.getChildren()) {
      next.setVisible(false);
    }

    if (additional != null) {

      AdditionalType additionalType = additional.getAdditionalType();
      Mask<AdditionalDetailController> additionalDetailControllerMask = masks.get(additionalType);
      AdditionalDetailController controller = additionalDetailControllerMask.getController();
      controller.setAdditional(additional);
      Mask<AdditionalDetailController> controllerMask = masks.get(additionalType);
      controllerMask.getRoot().setVisible(true);
    }

  }
}
