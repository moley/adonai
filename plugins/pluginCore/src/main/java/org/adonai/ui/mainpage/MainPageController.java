package org.adonai.ui.mainpage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.adonai.AdonaiProperties;
import org.adonai.ApplicationEnvironment;
import org.adonai.ServiceRegistry;
import org.adonai.actions.AddSessionAction;
import org.adonai.actions.AddSongAction;
import org.adonai.actions.ConfigurationAction;
import org.adonai.actions.ConnectSongWithMp3Action;
import org.adonai.actions.ExportAction;
import org.adonai.actions.LiveAction;
import org.adonai.actions.SearchAction;
import org.adonai.actions.SelectAction;
import org.adonai.additionals.AdditionalsImporter;
import org.adonai.api.MainAction;
import org.adonai.model.Additional;
import org.adonai.model.AdditionalType;
import org.adonai.model.Configuration;
import org.adonai.model.Model;
import org.adonai.model.Session;
import org.adonai.model.Song;
import org.adonai.model.SongBook;
import org.adonai.player.Mp3Player;
import org.adonai.services.AddSongService;
import org.adonai.services.RemoveSongService;
import org.adonai.services.SessionService;
import org.adonai.ui.AbstractController;
import org.adonai.ui.Consts;
import org.adonai.ui.SessionCellFactory;
import org.adonai.ui.SongCellFactory;
import org.adonai.ui.UiUtils;
import org.adonai.ui.editor2.SongEditor;
import org.controlsfx.control.Notifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainPageController extends AbstractController {

  @FXML private ToolBar tbaActions;

  @FXML private BorderPane border;

  @FXML private ToolBar tbTop;

  @FXML private StackPane spDetails;

  @FXML private ToggleButton togSongbooks;

  @FXML private ToggleButton togSessions;

  @FXML private ToggleButton togSession;

  @FXML private ListView<Song> lviSession;

  @FXML private ListView<Song> lviSongs;

  @FXML private ListView<Session> lviSessions;

  @FXML private VBox panSongDetails;

  @FXML private VBox panSessionDetails;

  @FXML private VBox panSessions;

  @FXML private VBox panSongbook;

  private MainPageContent currentContent;

  @FXML private Label lblCurrentEntity;

  @FXML private Label lblCurrentType;

  @FXML private MenuButton btnTenant;

  private FilteredList<Song> filteredSongList;


  private int iconSizeToolbar = Consts.ICON_SIZE_TOOLBAR;

  private static final Logger LOGGER = LoggerFactory.getLogger(MainPageController.class);



  @FXML private TextField txtSessionName;

  private Mp3Player mp3Player = new Mp3Player();

  private SongCellFactory songCellFactory = new SongCellFactory();


  public void setApplicationEnvironment (final ApplicationEnvironment applicationEnvironment){
    super.setApplicationEnvironment(applicationEnvironment);




    lviSongs.setUserData("mainpage.lviSongs");
    lviSongs.setCellFactory(new SongCellFactory());

    lviSession.setCellFactory(songCellFactory);
    lviSession.setPlaceholder(new Label("No songs in session available, press + to add ones"));

    lviSessions.setCellFactory(new SessionCellFactory());
    lviSessions.setPlaceholder(new Label("No sessions available, press + to add ones"));

    panSongDetails.setBackground(Background.EMPTY);

    panSessionDetails.setBackground(Background.EMPTY);

    reloadTenantData(applicationEnvironment.getCurrentTenant());

    refreshTenantButton();

    resetToolTip();


    txtSessionName.textProperty().addListener(new ChangeListener<String>() {
      @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        getCurrentSession().setName(newValue);
      }
    });

    selectSongbook();

    lviSongs.toFront();

    tbTop.minWidthProperty().bind(Bindings.max(border.widthProperty(), tbTop.prefWidthProperty()));

    //Button Plus
    Button btnAdd = new Button();
    btnAdd.setUserData("mainpage.btnAdd");
    btnAdd.setTooltip(new Tooltip("Add new item"));
    btnAdd.setGraphic(Consts.createIcon("fa-plus", iconSizeToolbar));
    tbaActions.setOrientation(Orientation.VERTICAL);
    tbaActions.getItems().add(btnAdd);
    btnAdd.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {

        event.consume();

        Configuration currentConfiguration = applicationEnvironment.getCurrentConfiguration();
        SongBook currentSongBook = applicationEnvironment.getCurrentSongBook();


        Bounds controlBounds = UiUtils.getBounds(btnAdd);
        Double x = controlBounds.getMinX() + 10;
        Double y = controlBounds.getMinY() - 20 - Consts.getDefaultHeight();

        //In Song details reimport content of current song
        if (currentContent.equals(MainPageContent.SONG)) {
          AddSongAction addSongHandler = new AddSongAction();
          addSongHandler.add(getApplicationEnvironment(), x, y, currentConfiguration, currentSongBook, new EventHandler<WindowEvent>() {
            @Override public void handle(WindowEvent event) {

              Song song = addSongHandler.getNewSong();
              LOGGER.info("New song " + song + " created");
              if (song != null) {
                AddSongService addSongService = new AddSongService();   //Add new song to songbook
                addSongService.addSong(song, currentSongBook);
                getApplicationEnvironment().setCurrentSong(song);
                refreshListViews(song);                        //Refresh list data and select the new song in editor
                selectSong(song);
              }
            }
          });
        } else //In Songbook add new song
          if (currentContent.equals(MainPageContent.SONGBOOK)) {
            AddSongAction addSongHandler = new AddSongAction();
            addSongHandler.add(getApplicationEnvironment(), x, y, currentConfiguration, currentSongBook, new EventHandler<WindowEvent>() {
              @Override public void handle(WindowEvent event) {

                Song song = addSongHandler.getNewSong();
                LOGGER.info("New song " + song + " created");
                if (song != null) {
                  SongBook songBook = currentSongBook;
                  AddSongService addSongService = new AddSongService();   //Add new song to songbook
                  addSongService.addSong(song, songBook);
                  refreshListViews(
                      song);                                 //Refresh list data and select the new song in editor
                  selectSong(song);
                }
              }
            });
          } else if (currentContent.equals(MainPageContent.SESSION)) { // in session add new song and add to session
            SelectAction<Song> selectSong = new SelectAction<Song>(getApplicationEnvironment());
            List<Song> allSongs = currentSongBook.getSongs();

            Double xSession = controlBounds.getMinX() + 10;
            Double ySession = controlBounds.getMinY() - 20 - SelectAction.SEARCHDIALOG_HEIGHT;

            selectSong.open(allSongs, xSession, ySession, new SongCellFactory(), new EventHandler<WindowEvent>() {
              @Override public void handle(WindowEvent event) {
                Song selectedSong = selectSong.getSelectedItem();
                LOGGER.info("handle window closed in selectsong on " + selectedSong);
                if (selectedSong != null) {
                  LOGGER.info("Add song " + selectedSong.getId() + " to session " + getCurrentSession().getName());
                  ServiceRegistry serviceRegistry = applicationEnvironment.getServices();
                  serviceRegistry.getSessionService().addSong(getCurrentSession(), selectedSong);
                  refreshListViews(selectedSong);
                }
              }
            });

          } else if (currentContent.equals(MainPageContent.SESSIONS)) {
            AddSessionAction addSessionAction = new AddSessionAction();
            Session session = addSessionAction.add(currentConfiguration);
            refreshListViews(null);
            selectSession(session);
          }
      }
    });

    //Button Minus
    Button btnRemove = new Button();
    btnRemove.setUserData("mainpage.btnRemove");
    btnRemove.setTooltip(new Tooltip("Remove selected item"));
    btnRemove.setGraphic(Consts.createIcon("fa-minus", iconSizeToolbar));
    tbaActions.getItems().add(btnRemove);
    btnRemove.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        LOGGER.info("Button minus was pressed");

        Configuration currentConfiguration = applicationEnvironment.getCurrentConfiguration();
        SongBook currentSongBook = applicationEnvironment.getCurrentSongBook();

        if (currentContent.equals(MainPageContent.SESSIONS)) {
          LOGGER.info("Remove session " + getCurrentSession().getName());
          currentConfiguration.getSessions().remove(getCurrentSession());
          refreshListViews(null);
        } else if (currentContent.equals(MainPageContent.SESSION)) {
          int selectedIndex = lviSession.getSelectionModel().getSelectedIndex();
          Session session = getCurrentSession();
          session.getSongs().remove(selectedIndex);
          int previous = Math.max(selectedIndex - 1, 0);
          lviSession.getSelectionModel().select(previous);
          refreshListViews(null);
        } else if (currentContent.equals(MainPageContent.SONGBOOK)) {
          RemoveSongService removeSongService = new RemoveSongService();
          removeSongService.removeSong(lviSongs.getSelectionModel().getSelectedItem(), currentSongBook);
          refreshListViews(null);
        }
      }
    });
    tbaActions.getItems().add(new Separator());
    Button btnMp3 = new Button();
    btnMp3.setTooltip(new Tooltip("Attach mp3 file to the song"));
    btnMp3.setUserData("mainpage.btnMp3");
    btnMp3.setGraphic(Consts.createIcon(AdditionalType.AUDIO.getIconName(), iconSizeToolbar));
    tbaActions.getItems().add(btnMp3);
    btnMp3.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {

        Configuration currentConfiguration = applicationEnvironment.getCurrentConfiguration();

        if (!currentContent.equals(MainPageContent.SESSIONS)) {
          ConnectSongWithMp3Action connectSongWithMp3Action = new ConnectSongWithMp3Action(getApplicationEnvironment());
          Bounds controlBounds = UiUtils.getBounds(btnMp3);
          Double x = controlBounds.getMinX() + 10;
          Double y = controlBounds.getMinY() - 20 - 600;
          connectSongWithMp3Action.connect(x, y, currentConfiguration, getSelectedSong());
        }
      }
    });

    tbaActions.getItems().add(new Separator());

    //Button Export transposed
    Button btnExport = new Button();
    btnExport.setTooltip(new Tooltip("Export to pdf"));
    btnExport.setUserData("mainpage.btnExport");
    btnExport.setGraphic(Consts.createIcon("fa-desktop", iconSizeToolbar));
    tbaActions.getItems().add(btnExport);
    btnExport.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        LOGGER.info("handle export action");
        Configuration currentConfiguration = applicationEnvironment.getCurrentConfiguration();

        Collection<Song> currentSongs = getCurrentSongs();
        if (!currentSongs.isEmpty()) {
          ExportAction exportAction = new ExportAction();
          exportAction.export(currentConfiguration, getCurrentSongs(), getExportName());
        } else
          LOGGER.warn("No songs selected to be exported");
      }
    });

    //Button Live Session
    Button btnLive = new Button();
    btnLive.setTooltip(new Tooltip("Live"));
    btnLive.setUserData("mainpage.btnLive");
    btnLive.setGraphic(Consts.createIcon("fa-music", iconSizeToolbar));
    tbaActions.getItems().add(btnLive);
    btnLive.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        LOGGER.info("handle export action");

        Configuration currentConfiguration = applicationEnvironment.getCurrentConfiguration();
        Collection<Song> currentSongs = getCurrentSongs();
        if (!currentSongs.isEmpty()) {
          LiveAction liveAction = new LiveAction();
          liveAction.startLiveSession(currentConfiguration, getCurrentSongs(), getExportName());
        } else
          LOGGER.warn("No songs selected to be exported");
      }
    });

    tbaActions.getItems().add(new Separator());

    Button btnPlayerBackward = new Button();
    btnPlayerBackward.setUserData("mainpage.btnPlayerBackward");
    btnPlayerBackward.setTooltip(new Tooltip("Step to the beginning of the current song"));
    btnPlayerBackward.setGraphic(Consts.createIcon("fa-backward", iconSizeToolbar));
    btnPlayerBackward.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        mp3Player.setFile(getMp3FileOfCurrentSong());
        mp3Player.beginning();
      }
    });
    tbaActions.getItems().add(btnPlayerBackward);

    Button btnPlayerStepBackward = new Button();
    btnPlayerStepBackward.setUserData("mainpage.btnPlayerStepBackward");
    btnPlayerStepBackward.setTooltip(new Tooltip("Step some seconds backward in the current song"));
    btnPlayerStepBackward.setGraphic(Consts.createIcon("fa-step-backward", iconSizeToolbar));
    btnPlayerStepBackward.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        mp3Player.setFile(getMp3FileOfCurrentSong());
        mp3Player.backward();
      }
    });
    tbaActions.getItems().add(btnPlayerStepBackward);

    Button btnPlayerPause = new Button();
    btnPlayerPause.setUserData("mainpage.btnPlayerPause");
    btnPlayerPause.setTooltip(new Tooltip("Pause playing the current song"));
    btnPlayerPause.setGraphic(Consts.createIcon("fa-pause", iconSizeToolbar));
    btnPlayerPause.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        mp3Player.setFile(getMp3FileOfCurrentSong());
        mp3Player.pause();
      }
    });
    tbaActions.getItems().add(btnPlayerPause);

    Button btnPlayerPlay = new Button();
    btnPlayerPlay.setUserData("mainpage.btnPlayerPlay");
    btnPlayerPlay.setTooltip(new Tooltip("Play the current song"));
    btnPlayerPlay.setGraphic(Consts.createIcon("fa-play", iconSizeToolbar));
    btnPlayerPlay.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        mp3Player.setFile(getMp3FileOfCurrentSong());
        mp3Player.play();
      }
    });
    tbaActions.getItems().add(btnPlayerPlay);

    Button btnPlayerStepForeward = new Button();
    btnPlayerStepForeward.setUserData("mainpage.btnPlayerStepForeward");
    btnPlayerStepForeward.setTooltip(new Tooltip("Step some seconds foreward in the current song"));
    btnPlayerStepForeward.setGraphic(Consts.createIcon("fa-step-forward", iconSizeToolbar));
    btnPlayerStepForeward.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        mp3Player.setFile(getMp3FileOfCurrentSong());
        mp3Player.forward();
      }
    });
    tbaActions.getItems().add(btnPlayerStepForeward);

    Button btnPlayerForeward = new Button();
    btnPlayerForeward.setUserData("mainpage.btnPlayerForeward");
    btnPlayerForeward.setTooltip(new Tooltip("Step to the end of the current song"));
    btnPlayerForeward.setGraphic(Consts.createIcon("fa-forward", iconSizeToolbar));
    btnPlayerForeward.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        mp3Player.setFile(getMp3FileOfCurrentSong());
        mp3Player.end();
      }
    });
    tbaActions.getItems().add(btnPlayerForeward);

    tbaActions.getItems().add(new Separator());

    //Button Save
    Button btnSave = new Button();
    btnSave.setTooltip(new Tooltip("Save all data"));
    btnSave.setUserData("mainpage.btnSave");
    btnSave.setGraphic(Consts.createIcon("fa-save", iconSizeToolbar));

    tbaActions.getItems().add(btnSave);
    btnSave.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        try {
          LOGGER.info("btnSave action recieved");

          applicationEnvironment.getModel().save();

          Notifications.create().title("Save").text("Model saved").show();
        } catch (Exception e) {
          Notifications.create().title("Save").text("Error occured while saving model").showError();
        }
      }
    });

    for (MainAction nextMainAction: applicationEnvironment.getPluginManager().getExtensions(MainAction.class)) {
      LOGGER.info("Add main action " + nextMainAction.getClass().getName());
      tbaActions.getItems().add(nextMainAction.createButton(applicationEnvironment));
    }


    //Button Configurations
    Button btnConfigurations = new Button();
    btnConfigurations.setTooltip(new Tooltip("Configure adonai"));
    btnConfigurations.setUserData("mainpage.btnConfigurations");

    btnConfigurations.setGraphic(Consts.createIcon("fa-wrench", iconSizeToolbar));
    tbaActions.getItems().add(btnConfigurations);
    btnConfigurations.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        LOGGER.info("Tenant before getCurrentConfiguration() screen " + applicationEnvironment.getAdonaiProperties().getCurrentTenant());

        Model model = applicationEnvironment.getModel();
        ConfigurationAction configurationAction = new ConfigurationAction(getApplicationEnvironment());
        configurationAction.openConfigurations(model, new EventHandler<WindowEvent>() {
          @Override public void handle(WindowEvent event) {
            refreshTenantButton();

          }
        });

      }
    });

    tbaActions.getItems().add(new Separator());

    //Button Exit
    Button btnExit = new Button();
    btnExit.setTooltip(new Tooltip("Exit adonai"));

    btnExit.setGraphic(Consts.createIcon("fa-power-off", iconSizeToolbar));
    tbaActions.getItems().add(btnExit);
    btnExit.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        Model model = applicationEnvironment.getModel();

        if (model.hasChanged()) {
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
          alert.setTitle("Exit adonai");
          alert.setHeaderText("You have unsaved changes!");
          alert.setContentText("Do you want to save your changes?");
          DialogPane dialogPane = alert.getDialogPane();
          alert.initStyle(StageStyle.UNDECORATED);
          dialogPane.setMinWidth(600);
          dialogPane.setMinHeight(200);
          UiUtils.applyCss(dialogPane.getScene());
          dialogPane.getStyleClass().add("myDialog");


          Optional<ButtonType> result = alert.showAndWait();
          if (result.get() == ButtonType.OK) {
            model.save();
          }
        }

        System.exit(0);
      }
    });

    //Views
    lviSongs.setOnKeyTyped(new EventHandler<KeyEvent>() {
      @Override public void handle(KeyEvent event) {
        LOGGER.info("handle lviSongs.KeyTyped <" + event.getText() + ">");

        if (event.getCode().equals(KeyCode.SPACE) || event.getText().equalsIgnoreCase("")) {
          LOGGER.info("Space typed, go to search action");
          SearchAction searchAction = new SearchAction(getApplicationEnvironment());
          Point2D point2D = lviSongs.localToScreen(lviSongs.getLayoutX() + 10, lviSongs.getHeight() - 55);
          searchAction.open(filteredSongList, "", point2D.getX(), point2D.getY());
        }
      }
    });

    togSongbooks.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        selectSongbook();
      }
    });

    togSessions.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        selectSessions();
      }
    });

    togSession.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {

        Configuration currentConfiguration = applicationEnvironment.getCurrentConfiguration();

        Session currentSession = applicationEnvironment.getCurrentSession();
        Session sessionToSelect = currentSession;
        if (sessionToSelect == null && !currentConfiguration.getSessions().isEmpty())
          sessionToSelect = currentConfiguration.getSessions().get(0);

        selectSession(sessionToSelect);
        event.consume();
      }
    });

    lviSessions.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() > 1) {
          LOGGER.info("handle onMouseClicked (" + lviSessions.getSelectionModel().getSelectedItem() + ")");
          selectSession(lviSessions.getSelectionModel().getSelectedItem());
          event.consume();
        }
      }
    });

    lviSession.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent event) {
        LOGGER.info("lviSession " + UiUtils.getBounds(btnExport));
        LOGGER.info("scene: " + UiUtils.getBounds(btnExport.getScene().getRoot()));
        LOGGER.info("mouseClicked on lviSession " + event.getClickCount() + "-" + event.getSource() + "-" + event
            .getSceneX() + "-" + event.getSceneY());
        LOGGER.info("btnExport  " + btnExport.isVisible() + "-" + UiUtils.getBounds(btnExport));
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() > 1) {
          selectSong(lviSession.getSelectionModel().getSelectedItem());
          event.consume();
        }
      }
    });

    lviSongs.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent event) {

        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() > 1) {
          LOGGER.info("doubleClick recieved on lviSongs");
          selectSong(lviSongs.getSelectionModel().getSelectedItem());
          event.consume();
        }
      }
    });

    refreshButtonState();

  }

  private File getMp3FileOfCurrentSong() {
    Song currentSong = getSelectedSong();
    if (currentSong == null) {
      LOGGER.info("getMp3FileOfCurrentSong returns null (currentSong=null)");
      return null;
    }
    if (currentSong.getAdditionals() == null) {
      LOGGER.info("getMp3FileOfCurrentSong returns null (getAdditionals=null)");
      return null;
    }
    AdditionalsImporter importer = new AdditionalsImporter();

    for (Additional nextAdditional : currentSong.getAdditionals()) {
      if (nextAdditional.getAdditionalType().equals(AdditionalType.AUDIO) && nextAdditional.getLink() != null) {
        File additionalFile = importer.getAdditionalFile(currentSong, nextAdditional);
        LOGGER.info("getMp3FileOfCurrentSong returns " + additionalFile);
        return additionalFile;
      }
    }

    LOGGER.info("getMp3FileOfCurrentSong returns null (not found) ");

    return null;
  }

  private void refreshButtonState() {
    Configuration currentConfiguration = getApplicationEnvironment().getCurrentConfiguration();
    boolean sessionsAvailable = currentConfiguration.getSessions().size() > 0;
    togSession.setVisible(sessionsAvailable);
  }

  private void reloadTenantData (String newTenant) {
    AdonaiProperties adonaiProperties = getApplicationEnvironment().getAdonaiProperties();
    adonaiProperties = new AdonaiProperties();
    String currentTenant = adonaiProperties.getCurrentTenant();
    LOGGER.info("Old tenant after getCurrentConfiguration() screen: " + currentTenant);
    LOGGER.info("New tenant after getCurrentConfiguration() screen: " + newTenant);
    if (! newTenant.equals(currentTenant)) {
      LOGGER.info("Tenant changed, reload data for tenant " + newTenant);
      adonaiProperties.setCurrentTenant(newTenant);
      adonaiProperties.save();
      refreshTenantButton();
      refreshListViews(null);
      selectSongbook();
    }
  }

  private void refreshTenantButton() {
    AdonaiProperties adonaiProperties = getApplicationEnvironment().getAdonaiProperties();
    Model model = getApplicationEnvironment().getModel();
    btnTenant.setText(adonaiProperties.getCurrentTenant());
    btnTenant.getItems().clear();
    for (String next: model.getTenantModelNames()) {
      MenuItem menuItem1 = new MenuItem(next);
      menuItem1.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent event) {
          MenuItem menuItem = (MenuItem) event.getSource();
          LOGGER.info("Selected " + menuItem.getText());
          adonaiProperties.setCurrentTenant(menuItem.getText());
          reloadTenantData(menuItem.getText());
        }
      });
      btnTenant.getItems().add(menuItem1);
    }


  }

  private void resetToolTip () {
    Label lblToolTipCurrentType = new Label();
    lblToolTipCurrentType.setText("Model: " + Consts.getAdonaiHome().getAbsolutePath());
    Tooltip tooltip = new Tooltip();
    tooltip.setGraphic(lblToolTipCurrentType);
    lblCurrentType.setTooltip(tooltip);
  }

  private void selectSessions() {
    LOGGER.info("select sessions");
    ApplicationEnvironment applicationEnvironment = getApplicationEnvironment();
    applicationEnvironment.setCurrentSong(null);
    currentContent = MainPageContent.SESSIONS;
    lblCurrentType.setText("sessions");
    lblCurrentEntity.setText("");
    spDetails.getChildren().clear();
    spDetails.getChildren().add(panSessions);
    lviSessions.requestFocus();
    refreshListViews(null);
    lviSessions.getSelectionModel().selectFirst();
    togSessions.setSelected(true);
  }

  private void selectSongbook() {
    LOGGER.info("selectSongbook on " + System.identityHashCode(this));
    ApplicationEnvironment applicationEnvironment = getApplicationEnvironment();
    applicationEnvironment.setCurrentSong(null);
    currentContent = MainPageContent.SONGBOOK;
    lblCurrentType.setText("songbook");
    lblCurrentEntity.setText("");
    spDetails.getChildren().clear();
    spDetails.getChildren().add(panSongbook);
    lviSongs.requestFocus();
    refreshListViews(null);
    lviSongs.getSelectionModel().selectFirst();
    lviSessions.getSelectionModel().selectFirst();
    lviSession.getSelectionModel().selectFirst();
    togSongbooks.setSelected(true);
  }

  private void selectSession(Session session) {
    LOGGER.info("select session " + session);
    if (session == null)
      return;

    currentContent = MainPageContent.SESSION;
    ApplicationEnvironment applicationEnvironment = getApplicationEnvironment();
    applicationEnvironment.setCurrentSong(null);
    applicationEnvironment.setCurrentSession(session);

    txtSessionName.setText(session.getName());

    spDetails.getChildren().clear();
    spDetails.getChildren().add(panSessionDetails);
    lviSession.requestFocus();
    lblCurrentType.setText("session");
    lblCurrentEntity.setText(applicationEnvironment.getCurrentSession().getName().toUpperCase());
    refreshListViews(null);
    lviSession.getSelectionModel().selectFirst();
    togSession.setSelected(true);

  }

  private void selectSong(Song song) {
    LOGGER.info("selectSong (" + song + ")");
    ApplicationEnvironment applicationEnvironment = getApplicationEnvironment();
    applicationEnvironment.setCurrentSong(song);
    Configuration currentConfiguration = applicationEnvironment.getCurrentConfiguration();

    currentContent = MainPageContent.SONG;
    SongEditor songEditor = new SongEditor(getApplicationEnvironment(), currentConfiguration, song);
    Parent songEditorPanel = songEditor.getPanel();
    songEditorPanel.setVisible(true);

    VBox.setVgrow(songEditorPanel, Priority.ALWAYS);
    panSongDetails.getChildren().clear();
    panSongDetails.getChildren().add(songEditorPanel);
    panSongDetails.toFront();
    panSongDetails.requestFocus();
    spDetails.getChildren().clear();
    spDetails.getChildren().add(panSongDetails);

    lblCurrentType.setText("song");
    String name = song.getName() != null ? song.getName().toUpperCase() : "UNDEFINED";
    lblCurrentEntity.setText(song.getId() + " - " + name);

    LOGGER.info("panSongDetails: " + panSongDetails.getWidth() + "-" + panSongDetails.getHeight());
    LOGGER.info("lviSongs: " + lviSongs.getWidth() + "-" + lviSongs.getHeight());

  }

  private Session getCurrentSession() {
    if (currentContent == MainPageContent.SESSIONS) {
      return lviSessions.getSelectionModel().getSelectedItem();
    } else if (currentContent == MainPageContent.SESSION) {
      return getApplicationEnvironment().getCurrentSession();
    } else
      throw new IllegalStateException("Invalid page content " + currentContent);
  }

  private Collection<Song> getCurrentSongs() {

    SongBook currentSongBook = getApplicationEnvironment().getCurrentSongBook();
    Session currentSession = getCurrentSession();
    Song currentSong = getApplicationEnvironment().getCurrentSong();


    if (currentContent == MainPageContent.SONGBOOK) {
      return getApplicationEnvironment().getCurrentSongBook().getSongs();
    } else if (currentContent == MainPageContent.SESSION) {
      SessionService sessionService = getApplicationEnvironment().getServices().getSessionService();

      return sessionService.getSongs(currentSession, currentSongBook);
    } else if (currentContent == MainPageContent.SONG) {
      if (currentSong == null)
        throw new IllegalStateException("No song selected in song view");
      return Arrays.asList(currentSong);
    } else if (currentContent == MainPageContent.SESSIONS) {
      if (currentSession != null) {
        SessionService sessionService = getApplicationEnvironment().getServices().getSessionService();
        return sessionService.getSongs(currentSession, currentSongBook);
      } else
        return new ArrayList<>();
    } else
      throw new IllegalStateException("Invalid page content " + currentContent);
  }

  private Song getSelectedSong() {
    if (currentContent == MainPageContent.SONGBOOK) {
      return lviSongs.getSelectionModel().getSelectedItem();
    } else if (currentContent == MainPageContent.SESSION) {
      return lviSession.getSelectionModel().getSelectedItem();
    } else if (currentContent == MainPageContent.SONG) {
      return getApplicationEnvironment().getCurrentSong();
    } else
      throw new IllegalStateException("Invalid page content " + currentContent);
  }

  private String getExportName() {
    if (currentContent == MainPageContent.SONGBOOK) {
      return "songbook";
    } else if (currentContent == MainPageContent.SESSION) {
      return getCurrentSession().getName();
    } else if (currentContent == MainPageContent.SONG) {
      return getApplicationEnvironment().getCurrentSong().getName();
    } else if (currentContent == MainPageContent.SESSIONS) {
      return getCurrentSession().getName();
    } else
      return "";
  }

  private void refreshListViews(Song selectSong) {
    LOGGER.info("refreshListViews (" + selectSong + ")");
    SongBook currentSongBook = getApplicationEnvironment().getCurrentSongBook();
    Session currentSession = getApplicationEnvironment().getCurrentSession();
    Configuration currentConfiguration = getApplicationEnvironment().getCurrentConfiguration();

    SessionService sessionService = getApplicationEnvironment().getServices().getSessionService();

    filteredSongList = new FilteredList<Song>(FXCollections.observableArrayList(currentSongBook.getSongs()),
        s -> true);
    lviSongs.setItems(filteredSongList);
    List<Song> sessionSongs = currentSession != null ?
        sessionService.getSongs(currentSession, currentSongBook) :
        new ArrayList<>();
    lviSession.setItems(FXCollections.observableArrayList(sessionSongs));
    songCellFactory.setSession(currentSession);
    lviSessions.setItems(FXCollections.observableArrayList(currentConfiguration.getSessions()));

    if (selectSong != null) {
      LOGGER.info("Select song " + selectSong);
      lviSongs.getSelectionModel().select(selectSong);
      lviSession.getSelectionModel().select(selectSong);
    }
  }

  public MainPageContent getCurrentContent() {
    return currentContent;
  }

}
