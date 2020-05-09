package org.adonai.fx.main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.adonai.AdonaiProperties;
import org.adonai.ApplicationEnvironment;
import org.adonai.SizeInfo;
import org.adonai.export.ExportConfiguration;
import org.adonai.export.ExportToken;
import org.adonai.export.presentation.PresentationDocumentBuilder;
import org.adonai.export.presentation.PresentationExporter;
import org.adonai.fx.AbstractController;
import org.adonai.fx.Consts;
import org.adonai.fx.ScreenManager;
import org.adonai.fx.editor.SongEditor;
import org.adonai.model.Configuration;
import org.adonai.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainController extends AbstractController {

  private static final Logger log = LoggerFactory.getLogger(MainController.class);

  public MenuButton btnMainActions;
  public Button btnPlayerBeginning;
  public Button btnPlayerBackward;
  public Button btnPlayerPlay;
  public Button btnPlayerPause;
  public Button btnPlayerForward;
  public Button btnPlayerEnd;
  public Label lblSongInfo;
  public HBox panHeader;
  public BorderPane border;
  public MenuButton btnTenant;
  public ComboBox cboScope;

  private ScreenManager screenManager = new ScreenManager();

  public void initialize() {
    btnMainActions.setGraphic(Consts.createIcon("fa-bars", Consts.ICON_SIZE_TOOLBAR));

    btnPlayerBeginning.setGraphic(Consts.createIcon("fa-backward", Consts.ICON_SIZE_TOOLBAR));
    btnPlayerBackward.setGraphic(Consts.createIcon("fa-step-backward", Consts.ICON_SIZE_TOOLBAR));
    btnPlayerPause.setGraphic(Consts.createIcon("fa-pause", Consts.ICON_SIZE_TOOLBAR));
    btnPlayerPlay.setGraphic(Consts.createIcon("fa-play", Consts.ICON_SIZE_TOOLBAR));
    btnPlayerForward.setGraphic(Consts.createIcon("fa-step-forward", Consts.ICON_SIZE_TOOLBAR));
    btnPlayerEnd.setGraphic(Consts.createIcon("fa-forward", Consts.ICON_SIZE_TOOLBAR));







    /**border.setOnKeyReleased(new EventHandler<KeyEvent>() {
      @Override public void handle(KeyEvent event) {
        log.info("Event " + event.getCode());
        if (event.getCode().equals(KeyCode.RIGHT)) {

          //TODO scope
          SongBook songBook = getApplicationEnvironment().getCurrentSongBook();
          int indexSong = songBook.getSongs().indexOf(getApplicationEnvironment().getCurrentSong());
          int newIndexSong = indexSong + 1;
          getApplicationEnvironment().setCurrentSong(songBook.getSongs().get(newIndexSong));
          log.info("Step to next song: " + getApplicationEnvironment().getCurrentSong().getId());
          reloadEditor();
        } else if (event.getCode().equals(KeyCode.LEFT)) {
          //TODO scope
          SongBook songBook = getApplicationEnvironment().getCurrentSongBook();
          int indexSong = songBook.getSongs().indexOf(getApplicationEnvironment().getCurrentSong());
          int newIndexSong = indexSong - 1;
          getApplicationEnvironment().setCurrentSong(songBook.getSongs().get(newIndexSong));
          log.info("Step to next song: " + getApplicationEnvironment().getCurrentSong().getId());
          reloadEditor();
        }
      }
    });**/
  }

  private void refreshTenantButton() {
    log.info("refreshTenantButton called");
    AdonaiProperties adonaiProperties = getApplicationEnvironment().getAdonaiProperties();
    Model model = getApplicationEnvironment().getModel();
    btnTenant.setText(adonaiProperties.getCurrentTenant());
    btnTenant.getItems().clear();
    for (String next : model.getTenantModelNames()) {
      MenuItem menuItem1 = new MenuItem(next);
      menuItem1.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent event) {
          MenuItem menuItem = (MenuItem) event.getSource();
          log.info("Selected " + menuItem.getText());
          adonaiProperties.setCurrentTenant(menuItem.getText());
          reloadTenantData(menuItem.getText());
        }
      });
      btnTenant.getItems().add(menuItem1);
    }

  }

  private void reloadTenantData(String text) {
    log.info("reloadTenantData " + text + " called");

    refreshTenantButton();

    SizeInfo sizeInfo = new SizeInfo(border.getWidth(), border.getHeight() - 200);
    log.info("get size: " + sizeInfo);
    log.info ("Screen: " + screenManager.getPrimary().getBounds());
    PresentationExporter exporter = new PresentationExporter(sizeInfo);

    Configuration configuration = getApplicationEnvironment().getCurrentConfiguration();
    ExportConfiguration exportConfiguration = configuration.findDefaultExportConfiguration(PresentationDocumentBuilder.class);

    exporter.export(getApplicationEnvironment().getCurrentSongBook().getSongs(), null, exportConfiguration);


    for (ExportToken next: exporter.getExportTokenContainer().getExportTokenList()) {
      //log.info("Next: " + next.getText() + " - " + next.getAreaInfo() + " - " + next.getExportTokenType());
    }

    SongEditor root = new SongEditor(exporter.getPanes());
    border.setCenter(root);
    root.show();

  }


  @Override public void setApplicationEnvironment(ApplicationEnvironment applicationEnvironment) {
    super.setApplicationEnvironment(applicationEnvironment);

    refreshTenantButton();

    Model model = getApplicationEnvironment().getModel();

    getApplicationEnvironment()
        .setCurrentSong(model.getCurrentTenantModel().get().getSongBooks().get(0).getSongs().get(0));

  }
}
