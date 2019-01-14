package org.adonai.actions;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.control.Control;
import javafx.stage.Modality;
import javafx.stage.WindowEvent;
import org.adonai.ui.Mask;
import org.adonai.ui.MaskLoader;
import org.adonai.ui.select.SelectController;

import java.util.ArrayList;
import java.util.List;


public class SelectAction<T> {

  public final static int SEARCHDIALOG_WIDTH = 400;
  public final static int SEARCHDIALOG_HEIGHT = 300;

  private Mask<SelectController> selectMask;

  public void open (final List<T> objects, final Control control, final EventHandler<WindowEvent> onCloseEventHandler) {
    List<T> objectsToAdd = new ArrayList<T>();
    objectsToAdd.addAll(objects);
    MaskLoader<SelectController> maskLoader = new MaskLoader<SelectController>();
    selectMask = maskLoader.load("select");
    selectMask.setSize(SEARCHDIALOG_WIDTH, SEARCHDIALOG_HEIGHT);
    selectMask.getStage().initModality(Modality.APPLICATION_MODAL);

    Bounds controlBounds = control.localToScreen(control.getLayoutBounds());
    selectMask.getStage().setX(controlBounds.getMaxX() + 20);
    selectMask.getStage().setY(controlBounds.getMaxY() - SEARCHDIALOG_HEIGHT);
    SelectController<T> controller = selectMask.getController();
    controller.getLviSelectItems().setItems(FXCollections.observableArrayList(objects));
    controller.getLviSelectItems().getSelectionModel().selectFirst();

    selectMask.getStage().setOnCloseRequest(onCloseEventHandler);

    selectMask.show();
  }

  public T getSelectedItem () {
    SelectController<T> controller = selectMask.getController();
    return controller.getSelectedItem();
  }

  public Mask<SelectController> getSelectMask () {
    return selectMask;
  }
}
