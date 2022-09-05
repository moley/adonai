package org.adonai.fx.imports.pages;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.adonai.ApplicationEnvironment;
import org.adonai.fx.imports.SongImportController;
import org.adonai.model.Song;
import org.adonai.model.SongBook;
import org.adonai.model.TenantModel;

@Slf4j
public class CloneFromTenantPage extends WizardPage {

  public final static String TITLE = "Clone existing song from other tenant";

  public CloneFromTenantPage(final ApplicationEnvironment applicationEnvironment, final Stage stage,
      final SongImportController controller) {
    super(applicationEnvironment, stage, TITLE, controller);
  }

  private ComboBox<String> cboTenant;
  private ListView<Song> lviSelectedItems;

  @Override public Parent getContent() {

    ObservableList<String> otherTenants = FXCollections.observableArrayList(applicationEnvironment.getOtherTenants());

    lviSelectedItems = new ListView<>();
    cboTenant = new ComboBox<>();
    cboTenant.setItems(otherTenants);
    cboTenant.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      TenantModel tenantModel = applicationEnvironment.getModel().getTenantModel(newValue);
      List<Song> allSongs = new ArrayList<>();
      for (SongBook songBook: tenantModel.get().getSongBooks()) {
        allSongs.addAll(songBook.getSongs());
      }
      log.info("Added " + allSongs + " songs for tenant " + tenantModel.getTenant());
      lviSelectedItems.setItems(FXCollections.observableArrayList(allSongs));
    });
    lviSelectedItems.getSelectionModel().selectedItemProperty().addListener(
        (observable, oldValue, newValue) -> controller.setSongToImport(newValue));

    return new VBox(5, cboTenant, lviSelectedItems);
  }

  public void nextPage() {
    if (! applicationEnvironment.getOtherTenants().isEmpty() && ! lviSelectedItems.getSelectionModel().isEmpty())
      navTo(PreviewPage.TITLE);
  }

}
