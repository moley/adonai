package org.adonai.actions;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.control.Control;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import org.adonai.ui.Mask;
import org.adonai.ui.MaskLoader;
import org.adonai.ui.select.SelectController;

import java.util.ArrayList;
import java.util.List;


public class SelectAction<T> {

  public final static int SEARCHDIALOG_WIDTH = 400;
  public final static int SEARCHDIALOG_HEIGHT = 600;

  private Mask<SelectController> selectMask;

  public void open (final List<T> objects, final Double x, final Double y, Callback<ListView<T>, ListCell<T>> cellFactory,
                    final EventHandler<WindowEvent> onCloseEventHandler) {
    List<T> objectsToAdd = new ArrayList<T>();
    objectsToAdd.addAll(objects);
    MaskLoader<SelectController> maskLoader = new MaskLoader<SelectController>();
    selectMask = maskLoader.load("select");
    selectMask.setSize(SEARCHDIALOG_WIDTH, SEARCHDIALOG_HEIGHT);
    selectMask.getStage().initModality(Modality.APPLICATION_MODAL);

    selectMask.getStage().setX(x);
    selectMask.getStage().setY(y);
    SelectController<T> controller = selectMask.getController();
    if (cellFactory != null)
      controller.getLviSelectItems().setCellFactory(cellFactory);

    controller.setFilteredData(new FilteredList<T>(FXCollections.observableArrayList(FXCollections.observableArrayList(objects)), s->true));
    controller.clearSelection();
    controller.getLviSelectItems().getSelectionModel().selectFirst();
    controller.getLviSelectItems().getStyleClass().add("selectlist");

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
