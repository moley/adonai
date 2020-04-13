package org.adonai.actions;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import org.adonai.ApplicationEnvironment;
import org.adonai.ui.Mask;
import org.adonai.ui.MaskLoader;
import org.adonai.ui.select.SelectController;

import java.util.ArrayList;
import java.util.List;


public class SelectAction<T> {

  public final static int SEARCHDIALOG_WIDTH = 400;
  public final static int SEARCHDIALOG_HEIGHT = 600;

  private Mask<SelectController> selectMask;

  private final ApplicationEnvironment applicationEnvironment;

  public SelectAction (final ApplicationEnvironment applicationEnvironment) {
    this.applicationEnvironment = applicationEnvironment;
  }

  public void open (final List<T> objects, final Double x, final Double y, Callback<ListView<T>, ListCell<T>> cellFactory,
                    final EventHandler<WindowEvent> onHiding) {
    List<T> objectsToAdd = new ArrayList<T>();
    objectsToAdd.addAll(objects);
    MaskLoader<SelectController> maskLoader = new MaskLoader<SelectController>();
    selectMask = maskLoader.load(applicationEnvironment, "select");
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

    selectMask.getStage().setOnHiding(onHiding);

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
